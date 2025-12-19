package com.example.apiestudo.service;

import com.example.apiestudo.dto.auth.LoginRequestDTO;
import com.example.apiestudo.dto.auth.LoginResponseDTO;
import com.example.apiestudo.dto.auth.RegisterRequestDTO;
import com.example.apiestudo.dto.user.UserRequestDTO;
import com.example.apiestudo.mapper.AuthMapper;
import com.example.apiestudo.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final AuthMapper authMapper;

    public AuthService(JwtService jwtService, AuthenticationManager authenticationManager, UserService userService, AuthMapper authMapper) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.authMapper = authMapper;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword()
        );

        Authentication authentication = authenticationManager.authenticate(auth);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(token, jwtService.getExpirationDate());
    }

    public void register(RegisterRequestDTO registerRequestDTO) {
        UserRequestDTO userRequestDTO = authMapper.convertRegisterRequestToUserRequest(registerRequestDTO);

        userService.create(userRequestDTO);
    }

}
