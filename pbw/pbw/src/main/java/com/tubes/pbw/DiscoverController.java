package com.tubes.pbw;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiscoverController {
    @GetMapping("/discover")
    public String redirectToDiscover() {
        return "discover";
    }
}
