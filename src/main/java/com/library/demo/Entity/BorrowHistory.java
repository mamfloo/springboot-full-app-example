package com.library.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class BorrowHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;
}
