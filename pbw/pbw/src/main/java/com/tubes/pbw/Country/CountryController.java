package com.tubes.pbw.Country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tubes.Data.Country;

@Controller
public class CountryController {
    
    @Autowired
    CountryService countryService;

}
