package com.library.demo.Service.impl;

import com.library.demo.Dto.JwtAuthenticationResponse;
import com.library.demo.Dto.LoginDto;
import com.library.demo.Dto.RefreshTokenRequest;
import com.library.demo.Dto.UserDto;
import com.library.demo.Entity.Role;
import com.library.demo.Entity.User;
import com.library.demo.Repository.UserRepository;
import com.library.demo.Service.AuthenticationService;
import com.library.demo.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserDto signUp(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(List.of(Role.USER));
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public JwtAuthenticationResponse signIn(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword()));
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Username or Password"));
        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userUsername = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByUsername(userUsername).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            String token = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(token);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }


}
