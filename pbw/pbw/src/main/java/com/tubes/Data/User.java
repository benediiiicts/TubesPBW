package com.tubes.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    private int id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 50, message = "username must be 4-50 chars")
    private String username;

    @NotNull
    @Size(min = 4, max = 60, message = "password must be 4-60 chars")
    private String password;

    @NotNull
    private String confirmpassword;

    private String role = "member";
}
