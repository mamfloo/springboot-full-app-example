package com.library.demo.Controller;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.BookEditDto;
import com.library.demo.Entity.Book;
import com.library.demo.Service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookDto bookDto){
        return ResponseEntity.ok(bookService.createBook(bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<BookDto> edit(@Valid @RequestBody BookEditDto bookEditDto){
        return ResponseEntity.ok(bookService.editBook(bookEditDto));
    }

}
