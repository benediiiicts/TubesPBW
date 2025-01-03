package com.tubes.pbw.User;

import java.util.Optional;

import com.tubes.Data.User;

public interface UserRepository {
    void save(User user) throws Exception;

    Optional<User> findByEmail(String email);
}
