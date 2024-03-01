package com.library.demo.Controller;

import com.library.demo.Dto.PublishingHouseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.library.demo.util.UtilMethods.asJsonString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PublishingHouseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void create() throws Exception{
        //given
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto("Fox");

        //when then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/publishingHouse")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(publishingHouseDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(publishingHouseDto.getName()));
    }

    @Test
    void createWithoutPermission() throws Exception{
        //given
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto("Fox");

        //when then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admin/publishingHouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publishingHouseDto)))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void delete() throws Exception{

        //given when then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/admin/publishingHouse/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteWithoutPermission() throws Exception{

        //given when then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/admin/publishingHouse/1"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}