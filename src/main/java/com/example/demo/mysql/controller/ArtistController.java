package com.example.demo.mysql.controller;

import com.example.demo.mysql.dto.ArtistDtos.ArtistRequest;
import com.example.demo.mysql.dto.ArtistDtos.ArtistResponse;
import com.example.demo.mysql.service.ArtistService;
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
 * REST controller for artist CRUD operations.
 */
@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<ArtistResponse> getAllArtists() {
        return artistService.findAll();
    }

    @GetMapping("/{id}")
    public ArtistResponse getArtistById(@PathVariable Integer id) {
        return artistService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ArtistResponse> createArtist(@RequestBody ArtistRequest request) {
        ArtistResponse created = artistService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ArtistResponse updateArtist(@PathVariable Integer id, @RequestBody ArtistRequest request) {
        return artistService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Integer id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
