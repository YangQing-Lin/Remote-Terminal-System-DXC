package com.group1.group1_backend.webssh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {
    @RequestMapping("/websshpage")
    public String websshpage(){
        return "webssh";
    }
}
