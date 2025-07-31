package com.library.demo.Service;

import com.library.demo.Entity.BorrowHistory;

import java.util.Optional;

public interface BorrowHistoryService {

    void save(BorrowHistory borrowHistory);

    BorrowHistory findLastNotReturnedByBookAndUser(Long bookId, Long userId);

}
