package com.tubes.pbw.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Country;

@Repository
public class JdbcCountryRepository implements CountryRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Country> getAllCountries() {
        String sql = "SELECT * FROM countries";
        List<Country> res = jdbcTemplate.query(sql, this::mapRowToCountries);
        return res;
    }

    @Override
    public Country getCountryByCode(String code) {
        String sql = "SELECT * FROM countries where country_code = ?";
        List<Country> res = jdbcTemplate.query(sql, this::mapRowToCountries, code);
        return res.get(0);
    }

    private Country mapRowToCountries(ResultSet rs, int rowNum) throws SQLException{
        return new Country(
            rs.getString("country_code"),
            rs.getString("country_name")
        );
    }
    
}
