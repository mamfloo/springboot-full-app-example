package com.library.demo.Service.impl;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Dto.UserDto;
import com.library.demo.Dto.UserOutputDto;
import com.library.demo.Entity.Book;
import com.library.demo.Entity.BorrowHistory;
import com.library.demo.Entity.User;
import com.library.demo.Repository.BookRepository;
import com.library.demo.Repository.UserRepository;
import com.library.demo.Service.*;
import com.library.demo.mapper.BookMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationService reservationService;
    private final BorrowHistoryService borrowHistoryService;


    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                System.err.println("CHIAMATO LOADBYUSERNAME");
                System.out.println(Thread.currentThread().getName());
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    @Transactional
    public void addBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        if(book.getUser() != null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book is not available");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            User user = userRepository.findByUsername(((UserDetails) principal).getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.getBooks().add(book);
            book.setUser(user);
            userRepository.save(user);
            BorrowHistory borrowHistory = new BorrowHistory();
            borrowHistory.setBook(book);
            borrowHistory.setUser(user);
            borrowHistory.setBorrowDate(LocalDateTime.now());
            borrowHistoryService.save(borrowHistory);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    @Transactional
    public void removeBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        if(book.getUser() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book already returned!");
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            User user = userRepository.findByUsername(((UserDetails) principal).getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            if(!book.getUser().getUsername().equals(user.getUsername())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            boolean removed = user.getBooks().removeIf(b -> Objects.equals(b.getId(), book.getId()));
            if(!removed){
                throw new IllegalArgumentException("Book not found");
            }
            book.setUser(null);
            userRepository.save(user);
            reservationService.notifyReservationUser(book);
            BorrowHistory borrowHistory = borrowHistoryService.findLastNotReturnedByBookAndUser(book.getId(), user.getId());
            borrowHistory.setReturnDate(LocalDateTime.now());
            borrowHistoryService.save(borrowHistory);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public static int[] getFrequenzaMaxArr(int[] numeri, int k){
        Map<Integer, Integer> conteggio = new HashMap<>();

        //prendo ogni num dall arr e vado a popolare la mappa
        for(int num: numeri){
            conteggio.put(num, conteggio.getOrDefault(num, 0) + 1);
        }

        //trasformo la mappa in una list dove la lista ha come tipo una Entry di Integer, Integer
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(conteggio.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));


        //metto tutto dentro un array e lo man do indietro
        int[] risultato = new int[k];
        for(int i = 0; i< risultato.length; i++){
            risultato[i] = list.get(i).getKey();
        }
        return risultato;
    }
}
