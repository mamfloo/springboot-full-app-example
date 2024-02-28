package com.library.demo.Service;

import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Entity.PublishingHouse;

public interface PublishingHouseService {

    public PublishingHouse createPublisingHouse(PublishingHouseDto publishingHouseDto);

    public void deletePublishingHouse(Long id);
}
