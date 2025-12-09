package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class Controller {
    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld() {

        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/endpoint")
    public String getMultipleParams(@RequestParam(required = false) String name) {
        return "Hello, " + (name != null ? name: "World") + "!";
    }
    @GetMapping("/add")
    public String add(@RequestParam int a, int b ) {
        return "Результат: " + (a +b);
    }
    @GetMapping("/subtract")
    public String subtract(@RequestParam int a, int b ) {
        return  "Результат: " + (a -b);
    }
    @GetMapping("/multiply")
    public String multiply(@RequestParam int a, int b ) {
        return "Результат: " + (a * b);
    }
    @GetMapping("/divide")
    public String divide(@RequestParam int a, int b ) {
        if(b == 0)
            return "Делить на 0 нельзя ";
        else
            return  "Результат: " + (a / b);
    }
}
