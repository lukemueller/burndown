package io.pivotal.parse;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
class ParseController {

    private Logger logger = LoggerFactory.getLogger(ParseController.class);

    @PostMapping(value = "/parse")
    void receiveUpdatedBurn(HttpServletRequest request,
                            @RequestPart("subject") String string,
                            @RequestPart("attachments") String attachmentCount,
                            @RequestPart("attachment-info") String attachmentInfo
    ) throws IOException, ServletException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(request.getPart("attachment1").getInputStream()))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
//                logger.debug(line);
            }
        }
    }
}
