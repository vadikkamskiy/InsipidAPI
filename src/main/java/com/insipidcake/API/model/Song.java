package com.insipidcake.API.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Song {
    String author;
    String title;
    String duration;
    String path;
    String coverImage;
}
