package com.leeturner.pinboard2markdown.service

import com.leeturner.pinboard2markdown.model.PinboardResponse
import com.leeturner.pinboard2markdown.model.Post
import org.springframework.stereotype.Component

@Component
class PinboardResponse2MarkdownConverter {

    fun convertPinboardResponse(pinboardResponse: PinboardResponse): String {
        return when {
            pinboardResponse.date.isBlank() && pinboardResponse.user.isBlank() -> ""
            else -> {
                val title = "# ${pinboardResponse.date} - ${pinboardResponse.user}"
                val posts = StringBuilder()
                pinboardResponse.posts.forEach { posts.append("\n\n${this.convertPost(it)}") }
                title + posts
            }
        }
    }

    fun convertPost(post: Post): String {
        return when {
            post.href.isBlank() && post.description.isBlank() -> ""
            else -> {
                val title = when {
                    post.href.isBlank() -> "## ${post.description}"
                    post.description.isBlank() -> "## [${post.href}](${post.href})"
                    else -> "## [${post.description}](${post.href})"
                }
                val extended = when {
                    post.extended.isBlank() -> ""
                    else -> "\n${post.extended}"
                }
                title + extended
            }
        }
    }
}