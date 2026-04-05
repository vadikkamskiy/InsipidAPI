package com.insipidcake.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insipidcake.API.entity.Song;


public interface SongsRepository extends JpaRepository<Song, Long>{
    
}
