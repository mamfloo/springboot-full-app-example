package com.library.demo.Dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class FiltroLibroDto {

    String nome;
    Double price;
    Long publishingHouseId;
}
