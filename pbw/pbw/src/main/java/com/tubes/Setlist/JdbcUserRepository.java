package com.tubes.Setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcSetlistRepository implements SetlistRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    
}
