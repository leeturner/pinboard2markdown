package com.leeturner.pinboard2markdown.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.util.*


@Configuration
class ApplicationConfig {
    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Bean
    fun restTemplate(): RestTemplate {
        // annoyingly the pinboard.in api return a content-type of "text/plain; charset=utf-8"
        // for their json responses.  This means we need to force the jackson message converter
        // to process the text/plain content type as well as the json one.
        val messageConverters: MutableList<HttpMessageConverter<*>> = ArrayList()
        val converter = MappingJackson2HttpMessageConverter()
        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.supportedMediaTypes = listOf(MediaType.ALL)
        messageConverters.add(converter)
        val restTemplate = RestTemplate()
        restTemplate.messageConverters = messageConverters
        return restTemplate
    }
}