package com.insipidcake.API.service;

import com.insipidcake.API.dto.GoogleTokenResponse;
import com.insipidcake.API.dto.GoogleUserInfo;
import com.insipidcake.API.dto.UserDto;
import com.insipidcake.API.entity.User;
import com.insipidcake.API.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GoogleAuthService {

    @Value("${clientID}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    // Desktop flow: URI должен совпадать с тем, что Flutter слушает
    @Value("${google.redirect-uri:http://127.0.0.1:3000}")
    private String redirectUri;

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public GoogleAuthService(ObjectMapper objectMapper,
                             UserRepository userRepository,
                             JwtService jwtService) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserDto authenticateWithGoogle(String code) throws IOException, InterruptedException {
        GoogleTokenResponse tokenResponse = exchangeCodeForToken(code);

        if (tokenResponse.getError() != null) {
            throw new RuntimeException("Google OAuth error: "
                    + tokenResponse.getError() + " - " + tokenResponse.getErrorDescription());
        }

        // 2. Получаем инфо о пользователе
        GoogleUserInfo userInfo = getUserInfo(tokenResponse.getAccessToken());

        // 3. Сохраняем пользователя в БД (если новый)
        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(userInfo.getEmail());
                    newUser.setName(userInfo.getName());
                    newUser.setPicture(userInfo.getPicture());
                    newUser.setRole("USER");
                    return userRepository.save(newUser);
                });

        // 4. Генерируем JWT
        String jwt = jwtService.generateToken(user);

        // 5. Возвращаем DTO
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setPicture(user.getPicture());
        dto.setJwt(jwt);

        return dto;
    }

    private GoogleTokenResponse exchangeCodeForToken(String code) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // В desktop flow redirect_uri должен совпадать с тем, что Flutter слушает
        String body = "code=" + code +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://oauth2.googleapis.com/token"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), GoogleTokenResponse.class);
    }

    private GoogleUserInfo getUserInfo(String accessToken) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/oauth2/v3/userinfo"))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), GoogleUserInfo.class);
    }
}