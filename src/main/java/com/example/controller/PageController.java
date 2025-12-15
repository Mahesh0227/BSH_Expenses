package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	  @GetMapping("/index")
	    public String index() {
	        return "index"; // index.html
	    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";  // templates/dashboard.html
    }

    @GetMapping("/auth")
    public String auth() {
        return "auth";  // templates/auth.html
    }
}
