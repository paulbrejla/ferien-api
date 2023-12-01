package de.paulbrejla.holidays.application.impl

import de.paulbrejla.holidays.application.api.RateLimitService
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Component("rateLimitService")
class GlobalRateLimitServiceImpl : RateLimitService {
    // For now we create one global bucket that all requests consume from.
    @Value("\${rateLimit.globalBucket.id}")
    var globalBucketId: String = ""

    @Value("\${rateLimit.globalBucket.capacity}")
    var globalBucketCapacity: Long = 0


    private var buckets: MutableMap<String, Bucket> = ConcurrentHashMap<String, Bucket>()
    override fun resolveBucket(bucketId: String): Bucket {
        return if (buckets.containsKey(bucketId)) {
            buckets[bucketId]!!
        } else {
            createBucket().let {
                buckets[bucketId] = it
                it
            }
        }
    }

    override fun fetchBucket(bucketId: String): Bucket? {
        return buckets[bucketId]
    }

    override fun isWithinQuota(maxRequests: Int, currentRequests: Int): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * We only allow 1000 requests per hour from now on. These 1000 requests are refilled when the new
     * hour starts.
     */
    private fun createBucket(): Bucket {
        return Bucket.builder()
            .addLimit(
                Bandwidth.classic(
                    globalBucketCapacity,
                    Refill.intervally(globalBucketCapacity, Duration.ofHours(1))
                )
            )
            .build()
    }
}