package com.davidramos1010.crudrapido.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.davidramos1010.crudrapido.dto.AuthResponse;
import com.davidramos1010.crudrapido.dto.LoginRequest;
import com.davidramos1010.crudrapido.dto.RegisterRequest;
import com.davidramos1010.crudrapido.dto.UserResponse;
import com.davidramos1010.crudrapido.entity.User;
import com.davidramos1010.crudrapido.repository.UserRepository;
import com.davidramos1010.crudrapido.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public UserResponse register(RegisterRequest req) {
        String email = req.email().trim().toLowerCase();
        if (userRepo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un usuario con ese email");
        }
        User u = new User();
        u.setName(req.name().trim());
        u.setEmail(email);
        u.setPassword(encoder.encode(req.password()));
        u = userRepo.save(u);
        return new UserResponse(u.getId(), u.getName(), u.getEmail());
    }

    public AuthResponse login(LoginRequest req) {
        User u = userRepo.findByEmail(req.email().trim().toLowerCase())
                .filter(x -> encoder.matches(req.password(), x.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));
        return new AuthResponse(jwtService.generateToken(u.getEmail()), "Bearer", jwtService.getExpirationMs());
    }
}