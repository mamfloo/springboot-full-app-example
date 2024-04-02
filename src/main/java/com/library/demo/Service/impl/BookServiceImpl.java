package com.library.demo.Service.impl;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.BookEditDto;
import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Entity.Book;
import com.library.demo.Entity.PublishingHouse;
import com.library.demo.Repository.BookRepository;
import com.library.demo.Repository.PublishingHouseRepository;
import com.library.demo.Service.BookService;
import com.library.demo.Service.PublishingHouseService;
import com.library.demo.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service()
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublishingHouseRepository publishingHouseRepository;
    private final ModelMapper modelMapper;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getBooks() {
        return bookMapper.bookListToBookDtoList(bookRepository.findAll());
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        PublishingHouse publishingHouse = publishingHouseRepository.findByName(bookDto.getPublishingHouseDto().getName())
                .orElseThrow(() -> new IllegalArgumentException("Publishing House not Found"));
        Book book = modelMapper.map(bookDto, Book.class);
        book.setPublishingHouse(publishingHouse);
        book = bookRepository.save(book);
        bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setPublishingHouseDto(new PublishingHouseDto(publishingHouse.getName()));
        log.warn("Book " + bookDto.getName() + " created");
        return bookDto;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto editBook(BookEditDto bookEditDto) {
        PublishingHouse publishingHouse = publishingHouseRepository.findByName(bookEditDto.getPublishingHouseDto().getName())
                .orElseThrow(() -> new IllegalArgumentException("Publishing House not Found"));
        Book book = modelMapper.map(bookEditDto, Book.class);
        book.setPublishingHouse(publishingHouse);
        book = bookRepository.save(book);
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setPublishingHouseDto(new PublishingHouseDto(publishingHouse.getName()));
        return bookDto;
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No book was found"));
    }
}
