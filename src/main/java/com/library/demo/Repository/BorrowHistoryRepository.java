package com.library.demo.Repository;

import com.library.demo.Entity.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {

    @Query("SELECT b from BorrowHistory b where b.book.id = :bookId and b.user.id = :userId and b.returnDate is null")
    Optional<BorrowHistory> findLastNotReturnedByBookAndUser(Long bookId, Long userId);
}
