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
    fun convertPinboardResponse() {
    }

    @Test
    fun `convertPost returns an empty string if no title or link`() {
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost(desc = "", href = ""))).isBlank()
    }

    @Test
    fun `convertPost with full post data returns a full markdown snippet`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/full-post.md")
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost())).isEqualTo(content)
    }

    @Test
    fun `convertPost with no url returns a markdown snippet with no link`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/no-url-post.md")
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost(href = ""))).isEqualTo(content)
    }

    @Test
    fun `convertPost with no description returns a markdown snippet with the link as the title`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/no-desc-post.md")
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost(desc = ""))).isEqualTo(content)
    }

    @Test
    fun `convertPost with no extended description returns a markdown snippet with just the title`() {
        val content = this.getMarkdownText("/expected-markdown-conversions/no-extended-desc-post.md")
        expectThat(this.post2MarkdownConverter.convertPost(TestUtils.createPost(extended = ""))).isEqualTo(content)
    }

    private fun getMarkdownText(path: String): String {
        val inputStream = this.javaClass.getResourceAsStream(path)
        return inputStream.bufferedReader().use(BufferedReader::readText)
    }
}