package com.library.demo.Controller;

import com.library.demo.Dto.UserDto;
import com.library.demo.Dto.UserOutputDto;
import com.library.demo.Entity.User;
import com.library.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PutMapping("/addBook/{id}")
    public ResponseEntity<Void> addBook(@PathVariable Long id){
        userService.addBook(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/removeBook/{id}")
    public ResponseEntity<Void> removeBook(@PathVariable Long id){
        userService.removeBook(id);
        return ResponseEntity.ok().build();
    }
}
