package de.paulbrejla.holidays.infrastructure.loader.impl

import biweekly.Biweekly
import de.paulbrejla.holidays.config.LoaderProperties
import de.paulbrejla.holidays.infrastructure.CalendarDto
import de.paulbrejla.holidays.infrastructure.assembleStateFromFileName
import de.paulbrejla.holidays.infrastructure.loader.api.CalendarLoader
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository
import org.eclipse.jgit.lib.RepositoryCache
import org.eclipse.jgit.revwalk.*
import org.eclipse.jgit.transport.RefSpec
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.eclipse.jgit.treewalk.TreeWalk
import org.eclipse.jgit.treewalk.filter.PathFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream
import javax.annotation.PostConstruct

@Component
@ConditionalOnProperty(prefix = "loader", name = ["source"], havingValue = "git")
class GitCalendarLoaderGateway(val loaderProperties: LoaderProperties) : CalendarLoader {
    val logger: Logger = LoggerFactory.getLogger(this::class.java)
    private val refSpec: String = "+refs/heads/*:refs/heads/*"

    override fun loadCalendarFiles(): List<CalendarDto> {
        return readFromGit().map {
            CalendarDto(assembleStateFromFileName(it.first), Biweekly.parse(it.second).all()).also { cal ->
                logger.info("Imported ${cal.calendars.size} entries for ${cal.state}")
            }
        }
    }

    @PostConstruct
    fun postConstruct() {
        logger.info("Using ${this.javaClass.name} for ICS import.")
    }

    private fun readFromGit(): List<Pair<String, String>> {
        val repoDesc = DfsRepositoryDescription()
        val repo = InMemoryRepository(repoDesc)

        val git = Git(repo)
        git.fetch()
            .setForceUpdate(true)
            .setRemote(loaderProperties.remoteURL)
            .setRefSpecs(RefSpec(refSpec)).also { fc ->
                loaderProperties.authToken?.let {
                    fc.setCredentialsProvider(UsernamePasswordCredentialsProvider(loaderProperties.authToken, ""))
                }
            }
            .call()

        val latestCommit: RevCommit = assembleLatestCommit(repo, loaderProperties.branch)
        val tree = latestCommit.tree
        val treeWalk = assembleTreeWithFilters(repo, tree, loaderProperties.filePath)

        return extractCalendarFiles(treeWalk, repo)
    }

    private fun assembleLatestCommit(repo: InMemoryRepository, branch: String): RevCommit {
        val lastCommitId = repo.resolve("refs/heads/$branch")
        val revWalk = RevWalk(repo)
        revWalk.sort(RevSort.COMMIT_TIME_DESC, true)
        return revWalk.parseCommit(lastCommitId)
    }

    private fun extractCalendarFiles(
        treeWalk: TreeWalk,
        repo: InMemoryRepository
    ): List<Pair<String, String>> {
        val calendarFiles = mutableListOf<Pair<String, String>>()

        while (treeWalk.next()) {
            try {
                val loader = repo.open(treeWalk.getObjectId(0))

                val stream = ByteArrayOutputStream()
                loader.copyTo(stream)
                val calendarFileWithFilename = Pair(treeWalk.pathString.substringAfterLast("/"), stream.toString())
                calendarFiles.add(calendarFileWithFilename)
            } catch (e: Exception) {
                logger.error("Error extracting object from git: $e")
            }
        }
        repo.close()
        RepositoryCache.clear()
        return calendarFiles
    }

    private fun assembleTreeWithFilters(
        repo: InMemoryRepository,
        tree: RevTree?,
        filePath: String
    ): TreeWalk {
        val treeWalk = TreeWalk(repo)
        treeWalk.addTree(tree)
        treeWalk.isRecursive = true
        treeWalk.filter = PathFilter.create(filePath)

        return treeWalk
    }
}