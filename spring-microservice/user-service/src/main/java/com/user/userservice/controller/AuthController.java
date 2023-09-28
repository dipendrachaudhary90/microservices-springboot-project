package com.user.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.user.userservice.model.User;
import com.user.userservice.model.dto.LoginDto;
import com.user.userservice.security.CustomUserServiceImpl;
import com.user.userservice.security.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.user.userservice.service.auth.AuthServiceImpl;
import com.user.userservice.service.auth.dto.AuthResponse;
import com.user.userservice.service.user.UserService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final CustomUserServiceImpl customUserService;
    private final AuthServiceImpl authService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) throws Exception {
        Authentication authentication = authentication(loginDto.getUsername(), loginDto.getPassword());
        AuthResponse response = new AuthResponse();
        try {
            final UserDetails userDetails = customUserService.loadUserByUsername(loginDto.getUsername());
            System.out.println("User Detail " + userDetails.getUsername());
            Map<String, String> token = jwtTokenProvider.generateToken(authentication);
            response.setAccessToken(token.get("accessToken"));
            response.setExpiredIn(token.get("expiredIn"));
            response.setRefreshToken(token.get("refreshToken"));
            response.setUser(userService.findByUsername(loginDto.getUsername()));
            String OS_NAME = System.getProperty("os.name");
            System.out.println("OS Name: "+OS_NAME);
        } catch (JsonProcessingException ex) {
            throw new BadCredentialsException("Bad Login Credentials");
        } catch (AuthenticationException ex) {
            log.info("Invalid User Authentication !!!");
            ex.printStackTrace();
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User saveUser = authService.createUser(user);
        return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
    }

    private Authentication authentication(String username, String password) throws Exception {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException ex) {
            log.info(" User Disabled !!");
            throw new Exception("USER_DISABLED", ex);
        } catch (BadCredentialsException ex) {
            log.error("Invalid Credential !!!");
            throw new Exception("INVALID_CREDENTIAL");
        }
        return authentication;
    }
}
