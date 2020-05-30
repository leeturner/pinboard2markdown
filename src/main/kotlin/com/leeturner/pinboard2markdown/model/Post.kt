package com.leeturner.pinboard2markdown.model

data class Post(
    val href: String,
    val description: String,
    val extended: String,
    val meta: String,
    val hash: String,
    val time: String,
    val shared: String,
    val toread: String,
    val tags: String
) {

    fun getTagsAsList(): List<String> {
        if (this.tags.isBlank()) {
            return emptyList()
        }
        return this.tags.split(" ")
    }
}
