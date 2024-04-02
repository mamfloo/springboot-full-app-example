package com.library.demo.Service;

import com.library.demo.Dto.ReservationDto;
import com.library.demo.Entity.Book;

import java.util.List;

public interface ReservationService {

    void addReservation(Long bookId);

    void removeReservation(Long bookId);

    void notifyReservationUser(Book book);

    List<ReservationDto> findAllByUsername();
}
