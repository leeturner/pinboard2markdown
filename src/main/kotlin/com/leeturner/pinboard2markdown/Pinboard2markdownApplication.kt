package com.leeturner.pinboard2markdown

import com.leeturner.pinboard2markdown.service.PinboardService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Pinboard2markdownApplication(val pinboardService: PinboardService) : CommandLineRunner {
    override fun run(vararg args: String?) {
        print(this.pinboardService.getPostsByTag("newsletter"))
    }
}

fun main(args: Array<String>) {
    runApplication<Pinboard2markdownApplication>(*args)
}
