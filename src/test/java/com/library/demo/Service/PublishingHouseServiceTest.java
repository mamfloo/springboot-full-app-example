package com.library.demo.Service;

import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Entity.PublishingHouse;
import com.library.demo.Repository.PublishingHouseRepository;
import com.library.demo.Service.impl.PublishingHouseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class PublishingHouseServiceTest {

    private PublishingHouseServiceImpl publishingHouseService;
    @Mock
    private PublishingHouseRepository publishingHouseRepository;
    private PublishingHouse publishingHouse;
    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        publishingHouseService = new PublishingHouseServiceImpl(publishingHouseRepository, modelMapper);

        publishingHouse = new PublishingHouse();
        publishingHouse.setName("Fox");
        publishingHouse.setId(1L);
    }

    @Test
    void createPublisingHouse() {
        //given
        String name = "Fox";
        PublishingHouseDto publishingHouseDto = new PublishingHouseDto();
        publishingHouseDto.setName(name);
        when(publishingHouseRepository.save(any())).thenReturn(publishingHouse);

        //when
        PublishingHouse publishingHouse = publishingHouseService.createPublisingHouse(publishingHouseDto);

        //then
        assertEquals(name, publishingHouse.getName());
        assertEquals(1L, publishingHouse.getId());
    }

    @Test
    void deletePublishingHouse() {
        //given
        doNothing().when(publishingHouseRepository).delete(ArgumentMatchers.any());
        when(publishingHouseRepository.findById(1L)).thenReturn(java.util.Optional.of(publishingHouse));

        //when
        publishingHouseService.deletePublishingHouse(1L);

        //then
        verify(publishingHouseRepository, times(1)).delete(ArgumentMatchers.any());

    }
}