package com.softgallery.issuemanagementbackEnd.controller;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class Home {
    JWTUtil jwtUtil;

    Home(JWTUtil jwtUtil) {
        this.jwtUtil=jwtUtil;
    }

    @GetMapping("/")
    public String test() {
        return "server is running";
    }

    @GetMapping("/auth/check")
    public String authTest() {
        return "token is valid";
    }

    @GetMapping("/admin")
    public String adminTest() {
        return "you are admin";
    }

    @GetMapping("/tester")
    public String testerTest() {
        return "you are TESTER";
    }

    @GetMapping("/pl")
    public String plTest() {
        return "you are PL";
    }

    @GetMapping("/developer")
    public String devTest() {
        return "you are developer";
    }

    @GetMapping("/home/user/info")
    public String showUserInfo(@RequestHeader(name="Authorization") String token) {
        String onlyToken=JWTUtil.getOnlyToken(token);
        return jwtUtil.getUserId(onlyToken);
    }
}
