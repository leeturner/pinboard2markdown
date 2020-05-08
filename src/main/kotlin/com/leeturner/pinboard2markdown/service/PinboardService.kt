package com.leeturner.pinboard2markdown.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.leeturner.pinboard2markdown.model.PinboardResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PinboardService(
    @Value("\${pinboard.apiRecentEndpoint}") val apiRecentEndpoint: String,
    val objectMapper: ObjectMapper,
    val restTemplate: RestTemplate
) {

    fun getPostsByTag(tag: String): PinboardResponse? {
        // deal with 401 unauthorized
        val responseEntity =  this.restTemplate.getForEntity("${this.apiRecentEndpoint}&tag=${tag}", PinboardResponse::class.java)
        return responseEntity.body
    }

}