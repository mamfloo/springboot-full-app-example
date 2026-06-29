package com.library.demo.Repository;

import com.library.demo.Entity.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {

    @Query("SELECT b from BorrowHistory b where b.book.id = :bookId and b.user.id = :userId and b.returnDate is null")
    Optional<BorrowHistory> findLastNotReturnedByBookAndUser(Long bookId, Long userId);

    @Query("SELECT bh from BorrowHistory bh where bh.returnDate is null and bh.borrowDate < :cutoffDate ")
    List<BorrowHistory> findAllNotReturned(LocalDateTime cutoffDate);
}
