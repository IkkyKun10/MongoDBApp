package com.riezki.mongoapp_springboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoappSpringbootApplication

fun main(args: Array<String>) {
	runApplication<MongoappSpringbootApplication>(*args)
}
