package com.uzay.securityschool.controller;

import com.uzay.securityschool.entity.Role;
import com.uzay.securityschool.entity.RoleEnum;
import com.uzay.securityschool.entity.User;
import com.uzay.securityschool.repository.RoleRepository;
import com.uzay.securityschool.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@RequestMapping("/admin")

public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    //REGİSTER METOTU DEĞİL ADMİN YETKİSİ İLE EKLENEN KULLANICI ADMİN OLUR
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        Optional<User> usernew = userRepository.findByUsername(user.getUsername());
        if (usernew.isPresent()) {
            return ResponseEntity.ok().body("user zaten ekli");
        }
        User addUser =new User();
        addUser.setUsername(user.getUsername());
        addUser.setPassword(passwordEncoder.encode(user.getPassword()));
        addUser.setEmail(user.getEmail());
        User save = userRepository.save(addUser);
        Role roles =new Role();
        roles.setName(RoleEnum.ROLE_ADMIN);
        roles.setUser(save);
        roleRepository.save(roles);
        return ResponseEntity.ok("kayıt başarılı");

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) { // Kullanıcının var olup olmadığını kontrol eder
            userRepository.deleteById(id);
            return ResponseEntity.ok("Kullanıcı başarıyla silindi");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı"); // 404 Not Found döndürür
        }
    }


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        User user = userRepository.findById(id).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı");
        }

        User userMevcut = userOptional.get();

        // Gelen username, email veya şifre null değilse güncelleme yapıyoruz
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            userMevcut.setUsername(user.getUsername());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userMevcut.setEmail(user.getEmail());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userMevcut.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        User savedUser = userRepository.save(userMevcut);

        // Şifre hariç diğer alanları döndürüyoruz
        savedUser.setPassword(null);

        return ResponseEntity.ok(savedUser);
    }



}
