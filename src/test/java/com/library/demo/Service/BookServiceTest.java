package com.library.demo.Service;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.BookEditDto;
import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Entity.Book;
import com.library.demo.Entity.PublishingHouse;
import com.library.demo.Repository.BookRepository;
import com.library.demo.Repository.PublishingHouseRepository;
import com.library.demo.Service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class BookServiceTest {

    @Mock
    private PublishingHouseRepository publishingHouseRepository;
    @Autowired
    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        bookService = new BookServiceImpl(bookRepository, publishingHouseRepository, modelMapper);
    }

    @Test
    void createBook() {
        //given
        String name = "Harry";
        Double price = 12.5;
        String ph = "Fox";

        BookDto bookDto = new BookDto();
        bookDto.setName(name);
        bookDto.setPrice(price);
        bookDto.setPublishingHouseDto(new PublishingHouseDto(ph));

        //set the data for the mock pub house
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setName("Fox");
        when(publishingHouseRepository.findByName("Fox")).thenReturn(Optional.of(publishingHouse));


        //when
        BookDto bookCreated = bookService.createBook(bookDto);

        //then
        assertThat(bookCreated).isNotNull();
        assertThat(bookCreated.getName()).isEqualTo(name);
        assertThat(bookCreated.getPublishingHouseDto().getName()).isEqualTo(ph);
        assertThat(bookCreated.getPrice()).isEqualTo(price);
    }


    @Test
    void editBook() {
        //given
        String name = "Potter";
        Double price = 16.5;
        String ph = "EA";

        BookEditDto bookEditDto = new BookEditDto();
        bookEditDto.setId(1l);
        bookEditDto.setName(name);
        bookEditDto.setPrice(price);
        bookEditDto.setPublishingHouseDto(new PublishingHouseDto(ph));

        //set the data for the mock pub house (new one)
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setName(ph);
        when(publishingHouseRepository.findByName(ph)).thenReturn(Optional.of(publishingHouse));

        //when
        BookDto bookEdited = bookService.editBook(bookEditDto);

        //then
        assertThat(bookEdited).isNotNull();
        assertThat(bookEdited.getName()).isEqualTo(name);
        assertThat(bookEdited.getPublishingHouseDto().getName()).isEqualTo(ph);
        assertThat(bookEdited.getPrice()).isEqualTo(price);

    }
}