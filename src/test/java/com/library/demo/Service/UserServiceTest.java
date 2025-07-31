package com.library.demo.Service;

import com.library.demo.Dto.UserOutputDto;
import com.library.demo.Entity.Book;
import com.library.demo.Entity.PublishingHouse;
import com.library.demo.Entity.Role;
import com.library.demo.Entity.User;
import com.library.demo.Repository.BookRepository;
import com.library.demo.Repository.UserRepository;
import com.library.demo.Service.impl.UserServiceImpl;
import com.library.demo.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class UserServiceTest {

    @Mock
    private BorrowHistoryService borrowHistoryService;
    @Mock
    private ReservationService reservationService;
    @Mock
    private UserRepository userRepository;
    private UserService userService;
    @Mock
    private BookRepository bookRepository;
    private ModelMapper modelMapper;

    private BookMapper bookMapper;


    private Book book;
    private User user;

    @BeforeEach
    void setUp(){
        modelMapper = new ModelMapper();
        bookMapper = new BookMapper(modelMapper);
        userService = new UserServiceImpl(userRepository, bookRepository, reservationService, borrowHistoryService);

        //mock the book repository
        book = new Book();
        book.setName("Harry");
        book.setId(1L);
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setName("Fox");
        book.setPublishingHouse(publishingHouse);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        //mock the user repository
        user = new User();
        user.setUsername("John");
        user.setRoles(List.of(Role.USER));
        List<Book> books = new ArrayList<>();
        user.setBooks(books);
        when(userRepository.findByUsername("John")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        //set the authentication
        // Set up the SecurityContext
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername("John");
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

    }


    @Test
    void addBook() {

        //given
        Long id = 1L;

        //when
        userService.addBook(1L);

        //then
        //2 times because 1 is done for loadByUsername for authentication purpose
        verify(userRepository, times(2)).findByUsername(any());
        verify(userRepository, times(1)).save(any());
        verify(bookRepository, times(1)).findById(any());
    }

    @Test
    void removeBook() {

        //given
        Long id = 1L;

        user.setBooks(new ArrayList<>(List.of(book)));

        //when
        userService.removeBook(1L);

        //then
        //2 times because 1 is done for loadByUsername for authentication purpose
        verify(userRepository, times(2)).findByUsername(any());
        verify(userRepository, times(1)).save(any());
        verify(bookRepository, times(1)).findById(any());
    }
}