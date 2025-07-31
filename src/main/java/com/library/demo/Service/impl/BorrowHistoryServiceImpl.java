package com.library.demo.Service.impl;

import com.library.demo.Entity.BorrowHistory;
import com.library.demo.Repository.BorrowHistoryRepository;
import com.library.demo.Service.BorrowHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowHistoryServiceImpl implements BorrowHistoryService {

    private final BorrowHistoryRepository borrowHistoryRepository;

    @Override
    public void save(BorrowHistory borrowHistory) {
        borrowHistoryRepository.save(borrowHistory);
    }

    @Override
    public BorrowHistory findLastNotReturnedByBookAndUser(Long bookId, Long userId) {
        log.info("bookid {}, userId {}", bookId, userId);
        return borrowHistoryRepository.findLastNotReturnedByBookAndUser(bookId, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No history found"));
    }


}
