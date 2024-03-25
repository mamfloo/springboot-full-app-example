package com.library.demo.Service;

import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Entity.PublishingHouse;

import java.util.List;

public interface PublishingHouseService {

    public List<PublishingHouseDto> getPublishingHouses();

    public PublishingHouse createPublisingHouse(PublishingHouseDto publishingHouseDto);

    public void deletePublishingHouse(Long id);
}
