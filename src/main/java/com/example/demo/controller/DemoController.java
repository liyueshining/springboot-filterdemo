package com.example.demo.controller;

import com.example.demo.entity.HMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(value = "/hello")
    public HMessage sayHello() {
        logger.info("mark");
        return new HMessage("hello Girl");
    }

}
