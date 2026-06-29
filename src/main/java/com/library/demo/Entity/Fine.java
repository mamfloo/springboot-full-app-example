package com.library.demo.Entity;

import com.library.demo.Dto.FIneStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime issuedOn;

    @Column(nullable = false)
    private LocalDateTime lastPaymentDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FIneStatus fineStatus =  FIneStatus.NOT_PAID;

    @ManyToOne
    private User user;

    @OneToOne
    private BorrowHistory borrowHistory;
}
