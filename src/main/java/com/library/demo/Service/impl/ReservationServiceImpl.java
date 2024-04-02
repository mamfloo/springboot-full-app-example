package com.library.demo.Service.impl;

import com.library.demo.Dto.ReservationDto;
import com.library.demo.Entity.Book;
import com.library.demo.Entity.Reservation;
import com.library.demo.Entity.User;
import com.library.demo.Repository.ReservationRepository;
import com.library.demo.Repository.UserRepository;
import com.library.demo.Service.BookService;
import com.library.demo.Service.MailService;
import com.library.demo.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookService bookService;
    private final UserRepository userRepository;
    private final MailService mailSevice;
    private final ModelMapper modelMapper;


    @Override
    public void addReservation(Long bookId) {
        Book book = bookService.findById(bookId);
        //check if book is available
        if (book.getUser() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already available");
        }
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof UserDetails){
            User user = userRepository.findByUsername(((UserDetails) obj).getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found!"));
            List<Reservation> userReservations = reservationRepository.findAllByUserUsername(user.getUsername());
            //check if user has an exiting reservation for this book
            userReservations.forEach(r -> {
                if(r.getBook().getId().equals(bookId)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have an active reservation for this book");
                }
            });
            Reservation reservation = new Reservation();
            reservation.setBook(book);
            reservation.setUser(user);
            reservation.setReservationTime(LocalDateTime.now());
            reservationRepository.save(reservation);
            mailSevice.sendEmail("Reservation Confirmation", "Hello your reservation was confirmed we are gonna notify you when the book is available", user.getEmail());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void removeReservation(Long id) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof UserDetails){
            User user = userRepository.findByUsername(((UserDetails) obj).getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found!"));
            Reservation reservationToDelete = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
            if(!reservationToDelete.getUser().getUsername().equals(user.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            reservationRepository.delete(reservationToDelete);
            mailSevice.sendEmail("Reservation Removed Confirmation", "Hello your reservation was removed.", user.getEmail());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void notifyReservationUser(Book book) {
        String[] emails = book.getReservations().stream().map(r -> r.getUser().getEmail()).toList().toArray(new String[0]);
        mailSevice.sendEmail("Book available", "Hello your reserved book is now available", emails);
    }

    @Override
    public List<ReservationDto> findAllByUsername() {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof UserDetails){
            User user = userRepository.findByUsername(((UserDetails) obj).getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            return reservationRepository.findAllByUserUsername(user.getUsername()).stream().map(r -> modelMapper.map(r, ReservationDto.class)).toList();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
