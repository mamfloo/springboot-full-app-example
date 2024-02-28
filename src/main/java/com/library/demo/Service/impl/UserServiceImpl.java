package com.library.demo.Service.impl;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Dto.UserDto;
import com.library.demo.Dto.UserOutputDto;
import com.library.demo.Entity.Book;
import com.library.demo.Entity.User;
import com.library.demo.Repository.BookRepository;
import com.library.demo.Repository.UserRepository;
import com.library.demo.Service.BookService;
import com.library.demo.Service.JwtService;
import com.library.demo.Service.UserService;
import com.library.demo.mapper.BookMapper;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final BookMapper bookMapper;


    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public UserOutputDto addBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            User user = userRepository.findByUsername(((UserDetails) principal).getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.getBooks().add(book);
            book.setUser(user);

            user = userRepository.save(user);
            List<BookDto> booksDtos = bookMapper.bookListToBookDtoList(user.getBooks());
            UserOutputDto userOutputDto = modelMapper.map(user, UserOutputDto.class);
            userOutputDto.setBooks(booksDtos);
            return userOutputDto;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public UserOutputDto removeBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            User user = userRepository.findByUsername(((UserDetails) principal).getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            boolean removed = user.getBooks().removeIf(b -> b.getId().equals(book.getId()));
            if(!removed){
                throw new IllegalArgumentException("Book not found");
            }

            book.setUser(null);
            user = userRepository.save(user);
            List<BookDto> booksDtos = bookMapper.bookListToBookDtoList(user.getBooks());
            UserOutputDto userOutputDto = modelMapper.map(user, UserOutputDto.class);
            userOutputDto.setBooks(booksDtos);
            return userOutputDto;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
