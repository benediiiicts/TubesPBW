package com.tubes.Repository;

import java.util.Optional;

import com.tubes.Data.User;

public interface UserRepository {
    void save(User user) throws Exception;
    Optional<User> findByEmail(String email);
} 