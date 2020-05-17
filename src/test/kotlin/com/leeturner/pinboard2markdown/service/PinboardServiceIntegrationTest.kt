package com.leeturner.pinboard2markdown.service

import com.leeturner.pinboard2markdown.Pinboard2markdownApplication
import com.leeturner.pinboard2markdown.model.PinboardResponse
import com.leeturner.pinboard2markdown.utils.TestUtils
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import strikt.api.expectThat
import strikt.assertions.isEmpty

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [Pinboard2markdownApplication::class, PropertySourcesPlaceholderConfigurer::class], initializers = [ConfigFileApplicationContextInitializer::class])
internal class PinboardServiceIntegrationTest {

    @MockBean
    lateinit var restTemplate: RestTemplate
    @Autowired
    lateinit var pinboardService: PinboardService
    @Value("\${pinboard.apiRecentEndpoint}")
    lateinit var apiRecentEndpoint: String

    @Test
    fun `getPostByTag retries when a ResourceAccessException is thrown`() {
        whenever(this.restTemplate.getForEntity(anyString(), eq(PinboardResponse::class.java))).thenThrow(ResourceAccessException("Error Accessing API"))

        expectThat(this.pinboardService.getPostsByTag(TestUtils.NEWSLETTER_TAG)).isEmpty()
        // restTemplate should be called twice due to exception that is thrown
        verify(this.restTemplate, times(2)).getForEntity("$apiRecentEndpoint&tag=${TestUtils.NEWSLETTER_TAG}", PinboardResponse::class.java)
    }
}