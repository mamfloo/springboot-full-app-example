package com.library.demo.Controller;

import com.library.demo.Dto.FineDto;
import com.library.demo.Dto.FineResponseDto;
import com.library.demo.Service.FineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository("/user/fine")
@RequiredArgsConstructor
public class FineController {

    private final FineService fineService;

    @PostMapping("/payFine/{id}")
    ResponseEntity<FineResponseDto> payFine(@PathVariable("id") Long id){
        return ResponseEntity.ok(fineService.payFine(id));
    }

    @PostMapping("/fines")
    ResponseEntity<List<FineDto>> getFInes(){
        return ResponseEntity.ok(fineService.findFines());
    }
}
