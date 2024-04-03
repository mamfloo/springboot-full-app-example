package com.library.demo.Entity;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
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
