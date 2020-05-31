package com.leeturner.pinboard2markdown.service

import com.leeturner.pinboard2markdown.model.PinboardResponse
import com.leeturner.pinboard2markdown.utils.TestUtils
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import strikt.api.expectThat
import strikt.assertions.*

internal class PinboardServiceUnitTest {

    private val mockRestTemplate: RestTemplate = mock()
    private val pinboardService = PinboardService(REST_ENDPOINT, this.mockRestTemplate)

    @Test
    fun `getPostsByTag returns the correct PinboardResponse when response status is OK`() {
        val pinboardResponse = TestUtils.createPinboardResponse()
        val responseEntity = ResponseEntity.ok(pinboardResponse)
        whenever(this.mockRestTemplate.getForEntity(anyString(), eq(PinboardResponse::class.java))).thenReturn(responseEntity)

        val response = this.pinboardService.getPostsByTag(TestUtils.NEWSLETTER_TAG)

        expectThat(response.user).isEqualTo(pinboardResponse.user)
        expectThat(response.date).isEqualTo(pinboardResponse.date)
        expectThat(response.posts).hasSize(1)
        expectThat(response.posts).containsExactly(pinboardResponse.posts)
        // the correct tag should be added to the end of the url
        verify(this.mockRestTemplate).getForEntity("${REST_ENDPOINT}&tag=${TestUtils.NEWSLETTER_TAG}", PinboardResponse::class.java)
    }

    @Test
    fun `getPostsByTag returns an empty PinboardResponse when response status is not OK`() {
        val pinboardResponse = TestUtils.createPinboardResponse()
        val responseEntity = ResponseEntity<PinboardResponse>(pinboardResponse, HttpStatus.UNAUTHORIZED)
        whenever(this.mockRestTemplate.getForEntity(anyString(), eq(PinboardResponse::class.java))).thenReturn(responseEntity)

        val response = this.pinboardService.getPostsByTag(TestUtils.NEWSLETTER_TAG)
        expectThat(response.date).isBlank()
        expectThat(response.user).isBlank()
        expectThat(response.posts).isEmpty()

        // the correct tag should be added to the end of the url
        verify(this.mockRestTemplate).getForEntity("${REST_ENDPOINT}&tag=${TestUtils.NEWSLETTER_TAG}", PinboardResponse::class.java)
    }

    @Test
    fun `getPostsByTag returns an empty PinboardResponse when the responseEntity body is null but the response status is OK`() {
        val responseEntity = ResponseEntity<PinboardResponse>(null, HttpStatus.OK)
        whenever(this.mockRestTemplate.getForEntity(anyString(), eq(PinboardResponse::class.java))).thenReturn(responseEntity)

        val response = this.pinboardService.getPostsByTag(TestUtils.NEWSLETTER_TAG)
        expectThat(response.date).isBlank()
        expectThat(response.user).isBlank()
        expectThat(response.posts).isEmpty()

        // the correct tag should be added to the end of the url
        verify(this.mockRestTemplate).getForEntity("${REST_ENDPOINT}&tag=${TestUtils.NEWSLETTER_TAG}", PinboardResponse::class.java)
    }

    @Test
    fun `getPostsByTag returns an empty PinboardResponse when an HttpClientErrorException is thrown`() {
        whenever(this.mockRestTemplate.getForEntity(anyString(), eq(PinboardResponse::class.java))).thenThrow(HttpClientErrorException(HttpStatus.UNAUTHORIZED))

        val response = this.pinboardService.getPostsByTag(TestUtils.NEWSLETTER_TAG)
        expectThat(response.date).isBlank()
        expectThat(response.user).isBlank()
        expectThat(response.posts).isEmpty()
    }

    @Test
    fun `apiAccessRecovery returns an empty PinboardResponse`() {
        val response = this.pinboardService.apiAccessResourceAccessExceptionRecovery(ResourceAccessException("Error Accessing API"))
        expectThat(response.date).isBlank()
        expectThat(response.user).isBlank()
        expectThat(response.posts).isEmpty()
    }

    companion object {
        private const val REST_ENDPOINT = "https://api.pinboard.in/v1/posts/recent?auth_token=token&count=10&format=json"
    }
}