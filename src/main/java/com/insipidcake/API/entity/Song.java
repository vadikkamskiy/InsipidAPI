package com.insipidcake.API.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "song")
public class Song {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String lyrics;
    
    private Integer duration;  // в секундах
    
    @Column(name = "file_path", nullable = false)
    private String filePath;
    
    @Column(name = "cover_image_path")
    private String coverImagePath;
    
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    
    @ManyToMany
    @JoinTable(
        name = "song_author",
        joinColumns = @JoinColumn(name = "song_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;
    
    // Конструкторы
    public Song() {}
    
    public Song(String title, String filePath) {
        this.title = title;
        this.filePath = filePath;
    }
    
    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getLyrics() {
        return lyrics;
    }
    
    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
    
    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getCoverImagePath() {
        return coverImagePath;
    }
    
    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }
    
    public Album getAlbum() {
        return album;
    }
    
    public void setAlbum(Album album) {
        this.album = album;
    }
    
    public Set<Author> getAuthors() {
        return authors;
    }
    
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}