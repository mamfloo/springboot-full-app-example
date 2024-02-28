package com.library.demo.Controller;

import com.library.demo.Dto.PublishingHouseDto;
import com.library.demo.Entity.PublishingHouse;
import com.library.demo.Service.PublishingHouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/publishingHouse")
@RequiredArgsConstructor
public class PublishingHouseController {

    private final PublishingHouseService publishingHouseService;

    @PostMapping
    public ResponseEntity<PublishingHouse> create(@Valid @RequestBody PublishingHouseDto publishingHouseDto){
        return ResponseEntity.ok().body(publishingHouseService.createPublisingHouse(publishingHouseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        publishingHouseService.deletePublishingHouse(id);
        return ResponseEntity.ok().build();
    }
}
