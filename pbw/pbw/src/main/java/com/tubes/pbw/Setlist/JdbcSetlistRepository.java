package com.tubes.pbw.Setlist;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.SetList;

@Repository
public class JdbcSetlistRepository implements SetlistRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(SetList setlist) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Optional<SetList> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    
}
