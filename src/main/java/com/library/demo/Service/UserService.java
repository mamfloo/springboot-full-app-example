package com.library.demo.Service;

import com.library.demo.Dto.UserDto;
import com.library.demo.Dto.UserOutputDto;
import com.library.demo.Entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();

    void addBook(Long id);

    void removeBook(Long id);
}
