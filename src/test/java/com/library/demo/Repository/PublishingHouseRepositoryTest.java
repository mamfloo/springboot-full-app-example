package com.library.demo.Repository;

import com.library.demo.Entity.PublishingHouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PublishingHouseRepositoryTest {

    @Autowired
    private PublishingHouseRepository publishingHouseRepository;

    @AfterEach
    void tearDown(){
        //so after each test we have a clean table
        publishingHouseRepository.deleteAll();
    }

    @Test
    void itShouldFindByName() {

        //given
        String expectedName = "FoxFire";
        PublishingHouse publishingHouse = new PublishingHouse();
        publishingHouse.setName(expectedName);
        publishingHouseRepository.save(publishingHouse);

        //when
        PublishingHouse founded = publishingHouseRepository.findByName(expectedName).orElse(null);

        //then
        assertNotNull(founded);
        assertEquals(expectedName, founded.getName());
    }
}