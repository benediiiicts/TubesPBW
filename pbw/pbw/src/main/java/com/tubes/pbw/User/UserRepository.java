package com.tubes.pbw.User;

import java.util.*;

import com.tubes.Data.User;

public interface UserRepository {
    void save(User user) throws Exception;
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void updateUserRole(String email, String role);
    void deleteUser(String email);
}
