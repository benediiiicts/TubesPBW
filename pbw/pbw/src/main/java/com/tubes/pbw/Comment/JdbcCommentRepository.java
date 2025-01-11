package com.tubes.pbw.Comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Comment;

@Repository
public class JdbcCommentRepository implements CommentRepository{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Comment> getComments(Integer idSetlist) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getComments'");
    }

    @Override
    public void deleteComments(Integer idComment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteComments'");
    }

    
}
