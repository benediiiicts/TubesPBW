package com.tubes.pbw.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        // Cari user berdasarkan email
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            // Jika email ditemukan dan password cocok
            return user.get();
        }
        // Jika tidak ditemukan atau password salah
        return null;
    }

    public boolean emailExists(String email) {
        // Cek apakah email sudah ada di database
        return userRepository.findByEmail(email).isPresent();
    }
}