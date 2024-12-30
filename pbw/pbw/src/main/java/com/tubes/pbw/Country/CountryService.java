package com.tubes.pbw.Country;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Country;

@Service
public class CountryService {
    
    @Autowired
    CountryRepository countryRepository;

    public List<Country> getAllCountries(){
        return this.countryRepository.getAllCountries();
    }
    public Country getCountryByCode(String code){
        return this.countryRepository.getCountryByCode(code);
    }
}
