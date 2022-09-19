package com.example.messenger.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MessengerApiApplication

fun main(args: Array<String>) {
	runApplication<MessengerApiApplication>(*args)
}
