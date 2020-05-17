package com.leeturner.pinboard2markdown

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [Pinboard2markdownApplication::class, PropertySourcesPlaceholderConfigurer::class], initializers = [ConfigFileApplicationContextInitializer::class])
class Pinboard2markdownApplicationTests {

    @Test
    fun contextLoads() {
    }

}
