package com.example.demo.mysql.controller;

import com.example.demo.mysql.dto.SongDtos.SongRequest;
import com.example.demo.mysql.dto.SongDtos.SongResponse;
import com.example.demo.mysql.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for song CRUD operations.
 */
@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<SongResponse> getAllSongs() {
        return songService.findAll();
    }

    @GetMapping("/{id}")
    public SongResponse getSongById(@PathVariable Integer id) {
        return songService.findById(id);
    }

    @PostMapping
    public ResponseEntity<SongResponse> createSong(@RequestBody SongRequest request) {
        SongResponse created = songService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public SongResponse updateSong(@PathVariable Integer id, @RequestBody SongRequest request) {
        return songService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Integer id) {
        songService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
