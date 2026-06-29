package com.library.demo.scheduled;


import com.library.demo.Entity.BorrowHistory;
import com.library.demo.Repository.BorrowHistoryRepository;
import com.library.demo.Service.FineService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FineScheduler {

    private final BorrowHistoryRepository borrowHistoryRepository;
    private final FineService fineService;

    private final Integer MAX_DAY_TO_RETURN = 3; // 3 days to return a book

    @Scheduled(cron = "0 0 00 * * *")
    void searchForUnreturnedBooks(){
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(MAX_DAY_TO_RETURN);
        List<BorrowHistory> borrowHistories = borrowHistoryRepository.findAllNotReturned(cutoffDate);
        borrowHistories.forEach(fineService::createFine);
    }
}
