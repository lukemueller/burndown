package io.pivotal.parse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@RestController
class ParseController {

    private Logger logger;

    public ParseController() {
        this.logger = LoggerFactory.getLogger(ParseController.class);
    }

    @PostMapping(value = "/parse")
    void receiveUpdatedBurn(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();

        logger.debug("contents", StreamUtils.copyToString(inputStream, Charset.forName("UTF-8")));
    }
}
