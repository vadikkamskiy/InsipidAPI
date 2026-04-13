package com.insipidcake.API.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.insipidcake.API.dto.UserDto;
import com.insipidcake.API.service.GoogleAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleAuthService googleAuthService;

    @PostMapping("/google")
    public ResponseEntity<?> handleGoogleCode(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        if (code == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "error", "Code is missing"));
        }

        try {
            UserDto userDto = googleAuthService.authenticateWithGoogle(code);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", userDto.getJwt(),
                    "user", Map.of(
                            "email", userDto.getEmail(),
                            "name", userDto.getName(),
                            "picture", userDto.getPicture()
                    )
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }
}