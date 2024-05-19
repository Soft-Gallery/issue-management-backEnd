package com.softgallery.issuemanagementbackEnd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class Home {
    @GetMapping("/")
    public String test() {
        return "server is running";
    }
}
