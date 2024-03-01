package com.library.demo.Controller;

import com.library.demo.Dto.JwtAuthenticationResponse;
import com.library.demo.Dto.LoginDto;
import com.library.demo.Dto.RefreshTokenRequest;
import com.library.demo.Dto.UserDto;
import com.library.demo.Entity.User;
import com.library.demo.Service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(authenticationService.signUp(userDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@Valid @RequestBody LoginDto loginDto){
        return ResponseEntity.ok(authenticationService.signIn(loginDto));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
