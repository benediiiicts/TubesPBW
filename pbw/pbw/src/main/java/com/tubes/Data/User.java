package com.tubes.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    @NotNull
    private int id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min=4, max=50)
    private String username;

    @NotNull
    @Size(min=4, max=60)
    private String password;

    @NotNull
    @Size(min=4, max=60)
    private String confirmpassword;

    private String role = "member";
}
