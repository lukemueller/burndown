package io.pivotal

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class BurndownApplication {

    fun main(args: Array<String>) {
        SpringApplication.run(BurndownApplication::class.java, *args)
    }
}
