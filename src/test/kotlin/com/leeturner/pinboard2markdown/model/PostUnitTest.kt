package com.leeturner.pinboard2markdown.model

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import strikt.assertions.containsExactlyInAnyOrder
import strikt.assertions.hasSize
import strikt.assertions.isEmpty

internal class PostUnitTest {

    @Test
    internal fun `getTagsAsList returns the correct tags with one tag`() {
        val post = this.getPost("tag")

        expectThat(post.getTagsAsList()).hasSize(1)
        expectThat(post.getTagsAsList()).containsExactly("tag")
    }

    @Test
    internal fun `getTagsAsList returns the correct tags with multiple tags`() {
        val post = this.getPost("tag1 tag2 tag3")

        expectThat(post.getTagsAsList()).hasSize(3)
        expectThat(post.getTagsAsList()).containsExactlyInAnyOrder("tag1", "tag2", "tag3")
    }

    @Test
    internal fun `getTagsAsList returns the correct tags with no tags`() {
        val post = this.getPost("")

        expectThat(post.getTagsAsList()).isEmpty()
    }

    @Test
    internal fun `getTagsAsList returns the correct tags with an empty string`() {
        val post = this.getPost("     ")

        expectThat(post.getTagsAsList()).isEmpty()
    }

    private fun getPost(tags: String): Post {
        return Post(href = "http://leeturner.me",
                description = "Lee's website",
                extended = "This is a longer description of the post",
                meta = "meta",
                hash = "hash",
                time = "time",
                shared = "no",
                toread = "no",
                tags = tags)
    }
}