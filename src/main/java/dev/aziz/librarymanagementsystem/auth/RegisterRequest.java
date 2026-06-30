package dev.aziz.librarymanagementsystem.auth;

import dev.aziz.librarymanagementsystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;

}