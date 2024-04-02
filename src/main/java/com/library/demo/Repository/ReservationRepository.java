package com.library.demo.Repository;

import com.library.demo.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("Select r from Reservation r WHERE r.user.username = :username")
    List<Reservation> findAllByUserUsername(@Param("username") String username);
}
