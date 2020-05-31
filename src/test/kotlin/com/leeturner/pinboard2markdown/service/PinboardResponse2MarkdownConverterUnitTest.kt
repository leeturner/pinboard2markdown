package com.leeturner.pinboard2markdown.service

import com.leeturner.pinboard2markdown.utils.TestUtils
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isBlank
import strikt.assertions.isEqualTo
import java.io.BufferedReader

internal class PinboardResponse2MarkdownConverterUnitTest {

    private val post2MarkdownConverter = PinboardResponse2MarkdownConverter()

    @Test
    internal fun `convertPinboardResponse returns an empty string if PinboardResponse is empty`() {
        expectThat(this.post2MarkdownConverter.convertPinboardResponse(TestUtils.createPinboardResponse())).isBlank()
    }

    @Test
    internal fun `convertPinboardResponse returns full markdown snippet with full PinboardResponse`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/full-pinboard-response.md")
        val pinboardResponse = TestUtils.createPinboardResponse("2020-05-06T20:58:54Z", "username", listOf(TestUtils.createPost()))
        expectThat(this.post2MarkdownConverter.convertPinboardResponse(pinboardResponse)).isEqualTo(content)
    }

    @Test
    internal fun `convertPinboardResponse returns just the title when no posts in the PinboardResponse`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/no-posts-pinboard-response.md")
        val pinboardResponse = TestUtils.createPinboardResponse("2020-05-06T20:58:54Z", "username", emptyList())
        expectThat(this.post2MarkdownConverter.convertPinboardResponse(pinboardResponse)).isEqualTo(content)
    }

    @Test
    internal fun `convertPinboardResponse returns multiple posts markdown when multiple posts in the PinboardResponse`() {
        val post1 = TestUtils.createPost()
        val post2 = TestUtils.createPost(desc = "Kaggle: Your Home for Data Science", href = "https://www.kaggle.com/", extended = "Inside Kaggle you’ll find all the code & data you need to do your data science work.")
        val content = this.getMarkdownText("/expected-markdown-conversions/multiple-post-pinboard-response.md")
        val pinboardResponse = TestUtils.createPinboardResponse("2020-05-06T20:58:54Z", "username", listOf(post1, post2))
        expectThat(this.post2MarkdownConverter.convertPinboardResponse(pinboardResponse)).isEqualTo(content)
    }

    @Test
    internal fun `convertPost returns an empty string if no title or link`() {
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost(desc = "", href = ""))).isBlank()
    }

    @Test
    internal fun `convertPost with full post data returns a full markdown snippet`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/full-post.md")
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost())).isEqualTo(content)
    }

    @Test
    internal fun `convertPost with no url returns a markdown snippet with no link`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/no-url-post.md")
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost(href = ""))).isEqualTo(content)
    }

    @Test
    internal fun `convertPost with no description returns a markdown snippet with the link as the title`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/no-desc-post.md")
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost(desc = ""))).isEqualTo(content)
    }

    @Test
    internal fun `convertPost with no extended description returns a markdown snippet with just the title`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/no-extended-desc-post.md")
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost(extended = ""))).isEqualTo(content)
    }

    private fun getMarkdownText(path: String): String {
        val inputStream = this.javaClass.getResourceAsStream(path)
        return inputStream.bufferedReader().use(BufferedReader::readText)
    }
}