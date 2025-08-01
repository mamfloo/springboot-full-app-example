package com.library.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Length(min = 4)
    @NotBlank
    private String username;
    @NotBlank
    @Length(min = 4)
    private String password;

}
