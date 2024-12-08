package com.tubes.pbw;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowsController {
    @GetMapping("/shows")
    public String redirectToShows() {
        return "shows";
    }
    @GetMapping("/add-show")
    public String redirectToAddShow() {
        return "add-show";
    }
}
