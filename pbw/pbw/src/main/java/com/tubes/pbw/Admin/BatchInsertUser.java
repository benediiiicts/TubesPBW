// package com.tubes.pbw.Admin;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.util.ArrayList;
// import java.util.List;

// public class BatchInsertUser {
// public static void main(String[] args) {
// // Membuat daftar pengguna
// List<AdminData> users = new ArrayList<>();
// // Data Admin
// users.add(
// new AdminData("admin@gmail.com", "Admin", "admin123", "admin"));

// // Memasukkan data pengguna ke database
// insertUsers(users);
// }

// public static void insertUsers(List<AdminData> users) {
// PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

// // Informasi koneksi database
// String jdbcUrl = "jdbc:postgresql://localhost:5432/tubesPBW";
// String dbUser = "postgres";
// String dbPassword = "postgres";

// try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser,
// dbPassword)) {
// String sql = "INSERT INTO users (email, username, password, role) VALUES (?,
// ?, ?, ?)";
// try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
// {
// // Tambahkan setiap data pengguna ke batch
// for (AdminData user : users) {
// preparedStatement.setString(1, user.getEmail());
// preparedStatement.setString(2, user.getUsername());
// preparedStatement.setString(3, passwordEncoder.encode(user.getPassword()));
// preparedStatement.setString(4, user.getRole());
// preparedStatement.addBatch();
// }

// // Eksekusi batch insert
// int[] rowsInserted = preparedStatement.executeBatch();
// System.out.println(rowsInserted.length + " users berhasil ditambahkan dengan
// password terenkripsi!");
// }
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
// }