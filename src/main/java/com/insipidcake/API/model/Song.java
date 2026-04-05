package com.insipidcake.API.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Song {
    String author;
    String title;
    String album;
    String duration;
    String path;
    String coverImage;
}
