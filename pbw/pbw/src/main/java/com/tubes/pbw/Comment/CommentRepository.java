package com.tubes.pbw.Comment;

import java.util.List;

import com.tubes.Data.Comment;

public interface CommentRepository {
    List<Comment> getComments(Integer idSetlist);
    void deleteComments(Integer idComment);
    Comment saveComment(Comment comment);
}