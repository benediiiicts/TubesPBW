package com.tubes.pbw.Country;

import java.util.List;

import com.tubes.Data.Country;

public interface CountryRepository {
    List<Country> getAllCountries();
    Country getCountryByCode(String code);
}
