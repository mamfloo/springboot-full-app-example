package com.library.demo.Controller;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Dto.UserDto;
import com.library.demo.Service.AuthenticationService;
import com.library.demo.Service.BookService;
import com.library.demo.Service.PublishingHouseService;
import com.library.demo.Service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PublishingHouseService publishingHouseService;



    @Test
    @Order(1)
    @WithMockUser(username = "test", authorities = {"USER"})
    void addBook() throws Exception{
        //given
        UserDto userDto = new UserDto("test", "test", "asdasd@gmailc.om");
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto("Fox");
        BookDto bookDto = new BookDto("potter", 12.4, new PublishingHouseDto("Fox"));
        //populate the test data
        publishingHouseService.createPublisingHouse(publishingHouseDto);
        bookService.createBook(bookDto);
        authenticationService.signUp(userDto);

        //when then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/user/addBook/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    void addBookWithoutAuthorities() throws Exception{
        //given

        //when then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/user/addBook/1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(3)
    @WithMockUser(username = "test", authorities = {"USER"})
    void removeBook() throws Exception{
        //given when then
        mockMvc.perform(MockMvcRequestBuilders
                    .put("/user/removeBook/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(4)
    void removeBookWithoutPermission() throws Exception{
        //given when then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/user/removeBook/1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}