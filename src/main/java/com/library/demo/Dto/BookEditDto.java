package com.library.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookEditDto {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private PublishingHouseDto publishingHouseDto;
}
