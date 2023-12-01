package de.paulbrejla.holidays.rest.impl

import de.paulbrejla.holidays.application.api.RateLimitService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

@Component
class RateLimitFilter(val rateLimitService: RateLimitService) : Filter {
    @Value("\${rateLimit.globalBucket.id}")
    var globalBucketId: String = ""

    @Value("\${rateLimit.globalBucket.capacity}")
    var globalBucketCapacity: Long = 0


    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        if (!this.shouldFulfillRequestWithinRateLimit(globalBucketId)) { // Maybe add additional x-rate-limit headers later.
            (response as HttpServletResponse).apply {
                this.status = HttpStatus.TOO_MANY_REQUESTS.value()
                this.addHeader("X-RateLimit-Limit", globalBucketCapacity.toString())
                this.addHeader("X-RateLimit-Remaining", "0")
            }
        } else {
            chain!!.doFilter(request, response)
        }
    }

    private fun shouldFulfillRequestWithinRateLimit(bucketId: String): Boolean {
        return rateLimitService.resolveBucket(bucketId)
            .tryConsumeAndReturnRemaining(1).run {
                if (this.isConsumed)
                    true
                else
                    false
            }
    }
}