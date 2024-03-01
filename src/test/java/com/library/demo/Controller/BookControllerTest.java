package com.library.demo.Controller;

import com.library.demo.Dto.BookDto;
import com.library.demo.Dto.BookEditDto;
import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Service.PublishingHouseService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static com.library.demo.util.UtilMethods.asJsonString;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PublishingHouseService publishingHouseService;

    @Test
    @Order(1)
    @WithMockUser(username = "test", authorities = {"ADMIN"})
    void create() throws Exception {
        //given
        BookDto bookDto = new BookDto("Potter", 12.5, new PublishingHouseDto("FireFox"));
        //save the publishing house before performin the api call
        //the app is made so you have to have the ph saved before
        publishingHouseService.createPublisingHouse(bookDto.getPublishingHouseDto());

        //when then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(bookDto.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishingHouseDto.name").value(bookDto.getPublishingHouseDto().getName()));
    }

    @Test
    void createWithoutPermission() throws Exception {
        //given
        BookDto bookDto = new BookDto("Pottder", 12.5, new PublishingHouseDto("FireFox2"));
        //save the publishing house before performin the api call
        //the app is made so you have to have the ph saved before
        publishingHouseService.createPublisingHouse(bookDto.getPublishingHouseDto());

        //when then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDto)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(3)
    void deleteWithoutPermission() throws Exception{
        //given when then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/admin/book/1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @Order(4)
    @WithMockUser(value = "test", authorities = {"ADMIN"})
    void delete() throws Exception{
        //given when then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/book/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(2)
    @WithMockUser(value = "test", authorities = {"ADMIN"})
    void edit() throws Exception{
        //given
        BookEditDto bookEditDto = new BookEditDto(1L, "Ciao", 1.4, new PublishingHouseDto("test"));
        publishingHouseService.createPublisingHouse(bookEditDto.getPublishingHouseDto());

        //when then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/admin/book")
                .content(asJsonString(bookEditDto))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookEditDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(bookEditDto.getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publishingHouseDto.name").value(bookEditDto.getPublishingHouseDto().getName()));
    }

    @Test
    void editWithoutPermission() throws Exception{
        //given
        BookEditDto bookEditDto = new BookEditDto(1L, "Ciao", 1.4, new PublishingHouseDto("test2"));
        publishingHouseService.createPublisingHouse(bookEditDto.getPublishingHouseDto());

        //when then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/admin/book")
                        .content(asJsonString(bookEditDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}