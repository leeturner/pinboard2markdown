package com.leeturner.pinboard2markdown.model

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.hasSize

internal class PostUnitTest {

    @Test
    fun `getTagsAsList returns the correct tags with one tag`() {
        val post = this.getPost("tag")

        expectThat(post.getTagsAsList()).hasSize(1)
        expectThat(post.getTagsAsList()).containsExactly("tag")
    }

    @Test
    fun `getTagsAsList returns the correct tags with multiple tags`() {
        val post = this.getPost("tag1 tag2 tag3")

        expectThat(post.getTagsAsList()).hasSize(3)
        expectThat(post.getTagsAsList()).containsExactlyInAnyOrder("tag1", "tag2", "tag3")
    }

    @Test
    fun `getTagsAsList returns the correct tags with no tags`() {
        val post = this.getPost("")

        expectThat(post.getTagsAsList()).hasSize(0)
    }

    @Test
    fun `getTagsAsList returns the correct tags with an empty string`() {
        val post = this.getPost("     ")

        expectThat(post.getTagsAsList()).hasSize(0)
    }

    private fun getPost(tags: String): Post {
        return Post(href = "http://leeturner.me",
                description = "Lee's website",
                extended = "",
                meta = "meta",
                hash = "hash",
                time = "time",
                shared = "no",
                toread = "no",
                tags = tags)
    }
}