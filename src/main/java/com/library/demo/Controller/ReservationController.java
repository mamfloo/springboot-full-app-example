package com.library.demo.Controller;

import com.library.demo.Dto.ReservationDto;
import com.library.demo.Service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{bookId}")
    public ResponseEntity<Void> addReservation(@PathVariable Long bookIid){
        reservationService.addReservation(bookIid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> removeReservation(@PathVariable Long reservationId){
        reservationService.removeReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<ReservationDto>> getAll(){
        return ResponseEntity.ok(reservationService.findAllByUsername());
    }
}
