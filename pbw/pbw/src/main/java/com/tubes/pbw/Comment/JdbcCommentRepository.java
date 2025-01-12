package com.tubes.pbw.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubes.Data.Comment;
import com.tubes.pbw.User.UserService;
@Repository
public class JdbcCommentRepository implements CommentRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @Override
    public List<Comment> getComments(Integer idSetlist) {
        String sql = "SELECT * FROM comment WHERE id_setlist = ?";
        return jdbcTemplate.query(sql, this::mapRowToComment, idSetlist);
    }

    @Override
    public void deleteComments(Integer idComment) {
        String sql = "DELETE FROM comment WHERE id = ?";
        jdbcTemplate.update(sql, idComment);
    }

    @Override
    public Comment saveComment(Comment comment) {
        // insert komen ke database (id = 0)
        String sql = "INSERT INTO comment (commentar, date, id_setlist, id_user) VALUES (?, ?, ?, ?) RETURNING id";
        Integer id = jdbcTemplate.queryForObject(sql, 
                                    Integer.class,
                                    comment.getCommentar(),
                                    comment.getDate(),
                                    comment.getId_setlist(),
                                    comment.getUser().getId());
        //ambil komentar yang telah dimasukkan
        sql = "SELECT * FROM comment WHERE id = ?";
        Comment result = jdbcTemplate.query(sql, this::mapRowToComment, id).get(0);
        return result;
    }

    private Comment mapRowToComment(ResultSet rs, int rowNum) throws SQLException {
        return new Comment(
            rs.getInt("id"),
            rs.getString("commentar"),
            rs.getDate("date"),
            rs.getInt("id_setlist"),
            userService.findById(rs.getInt("id_user")).get()
        );
    }
}