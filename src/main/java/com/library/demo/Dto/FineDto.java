package com.library.demo.Dto;

import java.time.LocalDateTime;

public class FineDto {
    private Long id;

    private Double amount;

    private LocalDateTime issuedOn;

    private LocalDateTime lastPaymentDate;

    private FIneStatus fineStatus;
}
