package com.tubes.pbw.Comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Comment;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getComments(Integer idSetlist) {
        return commentRepository.getComments(idSetlist);
    }

    public void deleteComments(Integer idComment) {
        commentRepository.deleteComments(idComment);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.saveComment(comment);
    }
}
