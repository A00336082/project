package com.example.demo.mysql.controller;

import com.example.demo.mysql.dto.PlaylistSongDtos.PlaylistSongRequest;
import com.example.demo.mysql.dto.PlaylistSongDtos.PlaylistSongResponse;
import com.example.demo.mysql.service.PlaylistSongService;
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
 * REST controller for playlist–song junction CRUD operations.
 */
@RestController
@RequestMapping("/api/playlist-songs")
public class PlaylistSongController {

    private final PlaylistSongService playlistSongService;

    public PlaylistSongController(PlaylistSongService playlistSongService) {
        this.playlistSongService = playlistSongService;
    }

    @GetMapping
    public List<PlaylistSongResponse> getAllPlaylistSongs() {
        return playlistSongService.findAll();
    }

    @GetMapping("/{id}")
    public PlaylistSongResponse getPlaylistSongById(@PathVariable Integer id) {
        return playlistSongService.findById(id);
    }

    @PostMapping
    public ResponseEntity<PlaylistSongResponse> createPlaylistSong(@RequestBody PlaylistSongRequest request) {
        PlaylistSongResponse created = playlistSongService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public PlaylistSongResponse updatePlaylistSong(
            @PathVariable Integer id,
            @RequestBody PlaylistSongRequest request) {
        return playlistSongService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylistSong(@PathVariable Integer id) {
        playlistSongService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
