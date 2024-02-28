package com.library.demo.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {

    @Length(min = 4)
    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    @NotEmpty
    @Email
    private String email;
}
