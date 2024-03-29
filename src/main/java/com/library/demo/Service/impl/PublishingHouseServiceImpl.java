package com.library.demo.Service.impl;

import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Entity.PublishingHouse;
import com.library.demo.Repository.PublishingHouseRepository;
import com.library.demo.Service.PublishingHouseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublishingHouseServiceImpl implements PublishingHouseService {

    private final PublishingHouseRepository publishingHouseRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<PublishingHouseDto> getPublishingHouses() {
        return  publishingHouseRepository.findAll().stream().map(ph -> modelMapper.map(ph, PublishingHouseDto.class)).collect(Collectors.toList());
    }

    @Override
    public PublishingHouse createPublisingHouse(PublishingHouseDto publishingHouseDto) {
        return publishingHouseRepository.save(modelMapper.map(publishingHouseDto, PublishingHouse.class));
    }

    @Override
    public void deletePublishingHouse(Long id) {
        publishingHouseRepository.delete(publishingHouseRepository.findById(id).get());
    }
}
