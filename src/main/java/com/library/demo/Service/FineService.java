package com.library.demo.Service;

import com.library.demo.Dto.FineDto;
import com.library.demo.Dto.FineResponseDto;
import com.library.demo.Entity.BorrowHistory;
import com.library.demo.Entity.User;

import java.util.List;

public interface FineService {

    void createFine(BorrowHistory borrowHistory);

    FineResponseDto payFine(Long id);

    List<FineDto> findFines();
}
