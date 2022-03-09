package net.gentledot.myservices.htmlservice.html.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HtmlServiceController {

    @GetMapping("/html/info")
    public String info(@Value("${server.port}") String port) {
        return String.format("HtmlService 서비스의 기본 동작 Port: %s", port);
    }

}
