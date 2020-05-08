package com.leeturner.pinboard2markdown.model

data class PinboardResponse(val date: String, val user: String, val posts: List<Post>)