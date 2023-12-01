package de.paulbrejla.holidays.application.api

import io.github.bucket4j.Bucket

interface RateLimitService {
    fun resolveBucket(bucketId: String): Bucket
    fun fetchBucket(bucketId: String): Bucket?
    fun isWithinQuota(maxRequests: Int, currentRequests: Int): Boolean
}