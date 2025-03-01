package com.woc.emailscheduler.controller;
//reg
import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping({"","/"})
    public String home(){
        return "index"; //index file
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact"; //contact file
    }
}
