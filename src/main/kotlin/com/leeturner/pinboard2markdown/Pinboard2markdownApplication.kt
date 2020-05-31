package com.leeturner.pinboard2markdown

import com.leeturner.pinboard2markdown.service.PinboardResponse2MarkdownConverter
import com.leeturner.pinboard2markdown.service.PinboardService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@SpringBootApplication
class Pinboard2markdownApplication(val pinboardService: PinboardService, val pinboardResponse2MarkdownConverter: PinboardResponse2MarkdownConverter) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val pinboardResponse = this.pinboardService.getPostsByTag("blogit")
        println(this.pinboardResponse2MarkdownConverter.convertPinboardResponse(pinboardResponse))
    }
}

fun main(args: Array<String>) {
    runApplication<Pinboard2markdownApplication>(*args)
}