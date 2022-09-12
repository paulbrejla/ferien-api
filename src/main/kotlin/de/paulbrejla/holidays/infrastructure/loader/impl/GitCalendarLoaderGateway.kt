package de.paulbrejla.holidays.infrastructure.loader.impl

import biweekly.Biweekly
import de.paulbrejla.holidays.config.LoaderProperties
import de.paulbrejla.holidays.infrastructure.CalendarDto
import de.paulbrejla.holidays.infrastructure.assembleStateFromFileName
import de.paulbrejla.holidays.infrastructure.loader.api.CalendarLoader
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.transport.RefSpec
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
        return readFromGit().map { CalendarDto(assembleStateFromFileName(it.first), Biweekly.parse(it.second).all()) }
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
            .setRemote(loaderProperties.remoteURL)
            .setRefSpecs(RefSpec(refSpec))
            .call()

        repo.objectDatabase

        val latestCommit: RevCommit = assembleLatestCommit(repo, loaderProperties.branch)
        val tree = latestCommit.tree
        val treeWalk = assembleTreeWithFilters(repo, tree, loaderProperties.filePath)

        return extractCalendarFiles(treeWalk, repo)
    }

    private fun assembleLatestCommit(repo: InMemoryRepository, branch: String): RevCommit {
        val lastCommitId = repo.resolve("refs/heads/$branch")
        val revWalk = RevWalk(repo)

        return revWalk.parseCommit(lastCommitId)
    }

    private fun extractCalendarFiles(
        treeWalk: TreeWalk,
        repo: InMemoryRepository
    ): List<Pair<String, String>> {
        var index = 0
        val calendarFiles = mutableListOf<Pair<String, String>>()
        while (treeWalk.next()) {
            val loader = repo.open(treeWalk.getObjectId(index))

            val stream = ByteArrayOutputStream()
            loader.copyTo(stream)
            val calendarFileWithFilename = Pair(treeWalk.pathString.substringAfterLast("/"), stream.toString())
            calendarFiles.add(calendarFileWithFilename)

            index++
        }

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