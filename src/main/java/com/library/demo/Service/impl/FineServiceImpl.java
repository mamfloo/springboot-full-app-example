package com.library.demo.Service.impl;

import com.library.demo.Dto.FIneStatus;
import com.library.demo.Dto.FineDto;
import com.library.demo.Dto.FineResponseDto;
import com.library.demo.Entity.BorrowHistory;
import com.library.demo.Entity.Fine;
import com.library.demo.Entity.User;
import com.library.demo.Repository.FineRepository;
import com.library.demo.Repository.UserRepository;
import com.library.demo.Service.FineService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void createFine(BorrowHistory borrowHistory) {
        Fine fine = new Fine();
        fine.setUser(borrowHistory.getUser());
        fine.setBorrowHistory(borrowHistory);
        fineRepository.save(fine);
    }

    @Override
    public FineResponseDto payFine(Long id) {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof UserDetails){
            User user = userRepository.findByUsername(((UserDetails) obj).getUsername()).orElseThrow(() -> new IllegalArgumentException("User not found"));
            Fine fine = user.getFines().stream().filter(f -> f.getId().equals(id))
                    .findFirst().orElseThrow(() -> new IllegalArgumentException("FIne not found"));
            if(user.getCredit() >= fine.getAmount()){
                user.setCredit(user.getCredit() - fine.getAmount());
                fine.setFineStatus(FIneStatus.PAID);
            }
            return new FineResponseDto(true);
        }
        return new FineResponseDto(false);
    }

    @Override
    public List<FineDto> findFines() {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(obj instanceof UserDetails){
            User user = (User) obj;
            List<Fine> fines = fineRepository.findFineByUserId(user.getId());
            return fines.stream().map(f -> modelMapper.map(f, FineDto.class)).toList();
        }
        throw new IllegalArgumentException("Something went wronf");
    }

}
