package com.tubes.pbw.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tubes.Data.Comment;
import com.tubes.Data.User;

import jakarta.servlet.http.HttpSession;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping("/comments/delete/{id}")
    @ResponseBody
    public Map<String, Object> deleteComment(@PathVariable("id") Integer idComment) {
        Map<String, Object> response = new HashMap<>();
        try {
            commentService.deleteComments(idComment);
            response.put("success", true);
            response.put("message", "Comment deleted successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error deleting comment");
        }
        return response;
    }

    @PostMapping("/comments/post")
    @ResponseBody
    public Map<String, Object> postComment(@RequestBody Map<String, Object> payload, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            response.put("success", false);
            response.put("message", "User not logged in");
            return response;
        }

        try {
            String commentText = (String) payload.get("comment");
            Integer setlistId = (Integer) payload.get("setlistId");
            
            Comment comment = new Comment(
                0, // ID will be auto-generated
                commentText,
                new Date(System.currentTimeMillis()), // Current date as java.sql.Date
                setlistId,
                loggedUser
            );
            
            Comment savedComment = commentService.saveComment(comment);
            
            response.put("success", true);
            response.put("comment", savedComment);
            response.put("username", loggedUser.getUsername());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error posting comment");
        }
        
        return response;
    }
}