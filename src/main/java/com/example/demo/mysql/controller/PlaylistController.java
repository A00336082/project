package com.example.demo.mysql.controller;

import com.example.demo.mysql.dto.PlaylistDtos.PlaylistRequest;
import com.example.demo.mysql.dto.PlaylistDtos.PlaylistResponse;
import com.example.demo.mysql.dto.PlaylistDtos.TopPlaylistResponse;
import com.example.demo.mysql.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for playlist CRUD and the Get_Top_Playlists stored procedure endpoint.
 */
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<PlaylistResponse> getAllPlaylists() {
        return playlistService.findAll();
    }

    /**
     * Calls the Get_Top_Playlists MySQL stored procedure.
     */
    @GetMapping("/top")
    public List<TopPlaylistResponse> getTopPlaylists(
            @RequestParam(defaultValue = "10") int limit) {
        return playlistService.getTopPlaylists(limit);
    }

    @GetMapping("/{id}")
    public PlaylistResponse getPlaylistById(@PathVariable Integer id) {
        return playlistService.findById(id);
    }

    @PostMapping
    public ResponseEntity<PlaylistResponse> createPlaylist(@RequestBody PlaylistRequest request) {
        PlaylistResponse created = playlistService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public PlaylistResponse updatePlaylist(
            @PathVariable Integer id,
            @RequestBody PlaylistRequest request) {
        return playlistService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Integer id) {
        playlistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
