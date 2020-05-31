package com.leeturner.pinboard2markdown.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNullOrEmpty

internal class PinboardResponseJsonConversionUnitTest {

    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Test
    internal fun `a PinboardResponse converts with one post`() {
        val inputStream = this.javaClass.getResourceAsStream("/pinboard-response-examples/one-post-response.json")
        val pinboardResponse = this.objectMapper.readValue(inputStream, PinboardResponse::class.java)

        expectThat(pinboardResponse.date).isEqualTo("2018-11-10T16:50:02Z")
        expectThat(pinboardResponse.user).isEqualTo("leeturner")
        expectThat(pinboardResponse.posts).hasSize(1)

        val post = pinboardResponse.posts[0]
        expectThat(post.href).isEqualTo( "https://www.kaggle.com/")
        expectThat(post.description).isEqualTo("Kaggle: Your Home for Data Science")
        expectThat(post.extended).isNullOrEmpty()
        expectThat(post.meta).isEqualTo("bdac04c26a2e94ce95c3fe4f402564f4")
        expectThat(post.hash).isEqualTo("e6ad936182532d34190def725f12e7a4")
        expectThat(post.time).isEqualTo("2018-11-10T16:50:02Z")
        expectThat(post.shared).isEqualTo("no")
        expectThat(post.toread).isEqualTo("no")
        expectThat(post.tags).isEqualTo("ai machine-learning deeplearning4j")
    }

    @Test
    internal fun `a PinboardResponse converts with a post with the minimum of data`() {
        val inputStream = this.javaClass.getResourceAsStream("/pinboard-response-examples/one-post-response-minimum-data.json")
        val pinboardResponse = this.objectMapper.readValue(inputStream, PinboardResponse::class.java)

        expectThat(pinboardResponse.date).isEqualTo("2020-05-07T18:54:14Z")
        expectThat(pinboardResponse.user).isEqualTo("leeturner")
        expectThat(pinboardResponse.posts).hasSize(1)

        val post = pinboardResponse.posts[0]
        expectThat(post.href).isEqualTo( "https://www.leeturner.me")
        expectThat(post.description).isNullOrEmpty()
        expectThat(post.extended).isNullOrEmpty()
        expectThat(post.meta).isEqualTo("d2dfe00e14e2ce32c0ed88a2c82d3ed2")
        expectThat(post.hash).isEqualTo("75796a7b60329ece32bcd6639fded9ab")
        expectThat(post.time).isEqualTo("2020-05-07T18:54:14Z")
        expectThat(post.shared).isEqualTo("no")
        expectThat(post.toread).isEqualTo("no")
        expectThat(post.tags).isNullOrEmpty()
    }

    @Test
    internal fun `a PinboardResponse converts with multiple posts`() {
        val inputStream = this.javaClass.getResourceAsStream("/pinboard-response-examples/multiple-post-response.json")
        val pinboardResponse = this.objectMapper.readValue(inputStream, PinboardResponse::class.java)

        expectThat(pinboardResponse.date).isEqualTo("2020-05-06T20:58:54Z")
        expectThat(pinboardResponse.user).isEqualTo("leeturner")
        expectThat(pinboardResponse.posts).hasSize(5)
    }
}