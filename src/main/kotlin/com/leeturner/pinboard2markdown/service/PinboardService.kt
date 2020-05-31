package com.leeturner.pinboard2markdown.service

import com.leeturner.pinboard2markdown.model.PinboardResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate

@Service
class PinboardService(
    @Value("\${pinboard.apiRecentEndpoint}") val apiRecentEndpoint: String,
    val restTemplate: RestTemplate
) {
    private val LOG = LoggerFactory.getLogger(PinboardService::class.java)

    @Retryable(value = [ResourceAccessException::class], maxAttempts = 2)
    fun getPostsByTag(tag: String): PinboardResponse {
        return try {
            LOG.info("Communicating with the Pinboard.in API...")
            val responseEntity = this.restTemplate.getForEntity("${this.apiRecentEndpoint}&tag=$tag", PinboardResponse::class.java)
            when (responseEntity.statusCode) {
                HttpStatus.OK -> responseEntity.body ?: logAndReturnEmptyResponse(tag)
                else -> logAndReturnEmptyResponse(tag)
            }
        } catch (httpClientErrorException: HttpClientErrorException) {
            // this Exception gets thrown when the api returns a 401 Unauthorised exception.
            // this probably happens because the pinboad.in api token hasn't been set correctly.
            // we catch this exception here instead of retrying because a retry is unlikely to
            // help in an unauthorised situation.
            logAndReturnEmptyResponse(httpClientErrorException)
        }
    }

    @Recover
    fun apiAccessResourceAccessExceptionRecovery(resourceAccessException: ResourceAccessException): PinboardResponse {
        return logAndReturnEmptyResponse(resourceAccessException)
    }

    private fun logAndReturnEmptyResponse(tag: String): PinboardResponse {
        LOG.info("No posts returned with the tag - {}", tag)
        return PinboardResponse("", "", listOf())
    }

    private fun logAndReturnEmptyResponse(exception: Exception): PinboardResponse {
        LOG.error("Can't access the Pinboard.in api.  Reason - {}", exception.message)
        return PinboardResponse("", "", listOf())
    }
}
