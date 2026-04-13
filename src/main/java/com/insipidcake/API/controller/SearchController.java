package com.insipidcake.API.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.insipidcake.API.model.Song;
import com.insipidcake.API.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    @PostMapping("/search")
    public List<Song> getResults(@RequestBody String query) {
        return searchService.webSearch(query);
    }
}
