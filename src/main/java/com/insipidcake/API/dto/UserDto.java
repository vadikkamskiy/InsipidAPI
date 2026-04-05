package com.insipidcake.API.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String picture;
    private String jwt;
}