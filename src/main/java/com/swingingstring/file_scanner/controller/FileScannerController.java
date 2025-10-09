package com.swingingstring.file_scanner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileScannerController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
