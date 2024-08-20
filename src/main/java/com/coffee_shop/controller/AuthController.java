package com.coffee_shop.controller;

import com.coffee_shop.domain.User;
import com.coffee_shop.dto.user.LoginDTO;
import com.coffee_shop.dto.user.RegisterDTO;
import com.coffee_shop.dto.user.ResponseDTO;
import com.coffee_shop.infra.security.TokenService;
import com.coffee_shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody LoginDTO dto){
        User user = this.userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("User not fund"));

        if (passwordEncoder.matches(dto.password(), user.getPassword())){
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody RegisterDTO dto){
        Optional<User> user = userRepository.findByEmail(dto.email());

        if (user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(dto.password()));
            newUser.setEmail(dto.email());
            newUser.setName(dto.name());
            this.userRepository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
