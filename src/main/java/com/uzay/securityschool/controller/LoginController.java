package com.uzay.securityschool.controller;

import com.uzay.securityschool.entity.User;
import com.uzay.securityschool.jsonwebtoken.JwtService;
import com.uzay.securityschool.security.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(MyUserDetailsService myUserDetailsService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.myUserDetailsService = myUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getUsername());

            // AuthenticationManager zaten password matching işlemini yapar
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword() // encode etmeden plain password
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);

            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok().body(token);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("kullanıcı bulunamadı, kayıt olun");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("giriş hatalı");
        }
    }








}
