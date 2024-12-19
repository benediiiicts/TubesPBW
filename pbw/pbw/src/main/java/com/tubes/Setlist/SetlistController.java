package com.tubes.Setlist;

import org.springframework.web.bind.annotation.GetMapping;

public class SetlistController {

    @GetMapping("/setlist")
    public String redirectToSetlist() {
        return "setlist";
    }
    @GetMapping("/add-setlist")
    public String redirectToAddSetlist() {
        return "add-setlist";
    }
    
}
