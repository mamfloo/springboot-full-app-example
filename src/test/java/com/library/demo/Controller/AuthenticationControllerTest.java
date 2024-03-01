package com.library.demo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.demo.Dto.LoginDto;
import com.library.demo.Dto.UserDto;
import com.library.demo.Entity.User;
import com.library.demo.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.library.demo.util.UtilMethods.asJsonString;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(1)
    void signUp() throws Exception{
        //given
        UserDto userDto = new UserDto("nick", "testtest", "tessdst@gmail.com");

        //when && then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signup")
                        .characterEncoding("utf-8")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(userDto.getUsername()));
    }

    @Test
    void signUpWrongUsername() throws Exception{
        //given
        UserDto userDto = new UserDto("i", "2323443244", "tessdst@gmail.com");

        //when && then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signup")
                        .characterEncoding("utf-8")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void signUpWrongPassword() throws Exception{
        //given
        UserDto userDto = new UserDto("nfick", "23", "tessdst@gmail.com");

        //when && then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signup")
                        .characterEncoding("utf-8")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void signUpWrongEmail() throws Exception{
        //given
        UserDto userDto = new UserDto("nfick", "23asdwsscxcs", "tessdst");

        //when && then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signup")
                        .characterEncoding("utf-8")
                        .content(asJsonString(userDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(2)
    void signIn() throws Exception{
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("nick");
        loginDto.setPassword("testtest");

        //when && then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signin")
                        .content(asJsonString(loginDto))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(Matchers
                        .allOf(notNullValue(), Matchers.not(Matchers.empty()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value(Matchers
                        .allOf(notNullValue(), Matchers.not(Matchers.empty()))));;
    }

    @Test
    @Order(2)
    void signInWrongCred() throws Exception{
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("nickers");
        loginDto.setPassword("testtest");

        //when && then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/signin")
                        .content(asJsonString(loginDto))
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


}