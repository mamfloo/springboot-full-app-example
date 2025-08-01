package com.library.demo.Service;

import com.library.demo.Dto.JwtAuthenticationResponse;
import com.library.demo.Dto.LoginDto;
import com.library.demo.Dto.RefreshTokenRequest;
import com.library.demo.Dto.UserDto;
import com.library.demo.Entity.User;

public interface AuthenticationService {


    JwtAuthenticationResponse signUp(UserDto userDto);

    JwtAuthenticationResponse signIn(LoginDto loginDto);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
