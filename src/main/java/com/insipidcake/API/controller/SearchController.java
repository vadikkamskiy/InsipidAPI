package com.insipidcake.API.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.insipidcake.API.entity.Song;
import com.insipidcake.API.service.SearchService;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    @PostMapping("/search")
    public List<Song> getLastSong(@RequestBody String entity) {
        return searchService.getLast(entity);
    }
    
}
