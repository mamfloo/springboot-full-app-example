package com.library.demo.mapper;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Entity.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper modelMapper;

    public List<BookDto> bookListToBookDtoList(List<Book> books){
        return books.stream().map(b -> {
            BookDto bookDto = modelMapper.map(b, BookDto.class);
            bookDto.setPublishingHouseDto(new PublishingHouseDto(b.getPublishingHouse().getName()));
            return bookDto;
        }).toList();
    }
}
