package com.library.demo.Dto;

import lombok.Data;

import java.util.List;

@Data
public class UserOutputDto {

    private String username;
    private String email;
    private List<BookDto> books;

}
