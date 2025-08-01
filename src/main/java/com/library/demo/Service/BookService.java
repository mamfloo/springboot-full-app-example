package com.library.demo.Service;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.BookEditDto;
import com.library.demo.Dto.FiltroLibroDto;
import com.library.demo.Entity.Book;
import java.util.List;

public interface BookService {

    public List<BookDto> getBooks();
    public List<BookDto> getBooks(FiltroLibroDto filtroLibroDto);

    public BookDto createBook(BookDto bookDto);

    public void deleteBook(Long id);

    public BookDto editBook(BookEditDto bookEditDto);

    public Book findById(Long id);
}
