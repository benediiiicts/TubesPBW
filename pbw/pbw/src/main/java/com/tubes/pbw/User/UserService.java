package com.tubes.pbw.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.tubes.Data.User;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean register(User user) {
        try {
            // Enkripsi password sebelum disimpan
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Simpan user ke database
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String email, String password) {
        // Cari user berdasarkan username
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            // Jika username ditemukan dan password cocok
            return user.get();
        }
        // Jika tidak ditemukan atau password salah
        return null;
    }
}
