package com.tubes.pbw.Error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    
    @GetMapping("/error")
    public String redirectError(){
        return "error";
    }
}
