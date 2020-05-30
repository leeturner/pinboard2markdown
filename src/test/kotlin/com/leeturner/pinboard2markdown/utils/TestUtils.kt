package com.leeturner.pinboard2markdown.utils

import com.leeturner.pinboard2markdown.model.PinboardResponse
import com.leeturner.pinboard2markdown.model.Post

object TestUtils {

    const val NEWSLETTER_TAG = "newsletter"

    fun createPinboardResponse(): PinboardResponse {
        val post1 = Post("https://www.kaggle.com/",
            "Kaggle: Your Home for Data Science",
            "",
            "bdac04c26a2e94ce95c3fe4f402564f4",
            "e6ad936182532d34190def725f12e7a4",
            "2018-11-10T16:50:02Z",
            "no",
            "no",
            "ai machine-learning deeplearning4j $NEWSLETTER_TAG")
        return PinboardResponse("2020-05-06T20:58:54Z", "username", listOf(post1))
    }

    fun createPost(desc: String = "Strikt",
                        href: String = "https://strikt.io/",
                        extended: String = "Strikt is an assertion library for Kotlin intended for use with a test runner such as JUnit or Spek.",
                        tags: String = NEWSLETTER_TAG): Post {
        return Post(href = href,
            description = desc,
            extended = extended,
            meta = "meta",
            hash = "hash",
            time = "time",
            shared = "no",
            toread = "no",
            tags = tags)
    }
}