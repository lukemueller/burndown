package io.pivotal.parse

import org.apache.catalina.servlet4preview.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

@RestController
internal class ParseController {

    private val logger: Logger

    init {
        this.logger = LoggerFactory.getLogger(ParseController::class.java)
    }

    @PostMapping(value = "/parse")
    fun receiveUpdatedBurn(request: HttpServletRequest) {
        val inputStream = request.inputStream

        logger.debug("contents", StreamUtils.copyToString(inputStream, Charset.forName("UTF-8")))
    }
}
