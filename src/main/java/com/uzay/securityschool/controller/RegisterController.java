package com.uzay.securityschool.controller;

import com.uzay.securityschool.entity.Role;
import com.uzay.securityschool.entity.RoleEnum;
import com.uzay.securityschool.entity.User;
import com.uzay.securityschool.jsonwebtoken.JwtService;
import com.uzay.securityschool.repository.RoleRepository;
import com.uzay.securityschool.repository.UserRepository;
import com.uzay.securityschool.security.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService myUserDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RegisterController(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtService jwtService, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?>registerUser(@Valid @RequestBody User user) {
        //eğer kullanıcı varsa boşuna kaydetmeyelim
        try {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getUsername());
            return ResponseEntity.badRequest().body("kullanıcı zaten kayıtlı");
        }
        catch (Exception e) {
            //kulanıcı yok demektir ekleyelim

            User newuser=new User();
            newuser.setUsername(user.getUsername());
            newuser.setPassword(passwordEncoder.encode(user.getPassword()));
            newuser.setEmail(user.getEmail());
            User saved = userRepository.save(newuser);
            Role role =new Role();
            role.setName(RoleEnum.ROLE_USER);
            role.setUser(newuser);
            roleRepository.save(role);



            return ResponseEntity.ok().body("kayıt başarılı" );


        }





    }



}

