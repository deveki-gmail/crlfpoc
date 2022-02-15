package com.concretepage.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {     
	
    @RequestMapping("/world")
    public String helloMsg() {
    	String msg = "Hello World!";
        return msg;
    }
}    
                                