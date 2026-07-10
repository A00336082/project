package com.example.demo.mongo.controller;

import com.example.demo.mongo.dto.SongReviewDtos.SongReviewRequest;
import com.example.demo.mongo.dto.SongReviewDtos.SongReviewResponse;
import com.example.demo.mongo.service.SongReviewService;
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
 * REST controller for song_reviews MongoDB CRUD operations.
 */
@RestController
@RequestMapping("/api/reviews")
public class SongReviewController {

    private final SongReviewService songReviewService;

    public SongReviewController(SongReviewService songReviewService) {
        this.songReviewService = songReviewService;
    }

    @GetMapping
    public List<SongReviewResponse> getAllReviews() {
        return songReviewService.findAll();
    }

    @GetMapping("/song/{songId}")
    public SongReviewResponse getReviewBySongId(@PathVariable Integer songId) {
        return songReviewService.findBySongId(songId);
    }

    @GetMapping("/{id}")
    public SongReviewResponse getReviewById(@PathVariable String id) {
        return songReviewService.findById(id);
    }

    @PostMapping
    public ResponseEntity<SongReviewResponse> createReview(@RequestBody SongReviewRequest request) {
        SongReviewResponse created = songReviewService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public SongReviewResponse updateReview(
            @PathVariable String id,
            @RequestBody SongReviewRequest request) {
        return songReviewService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        songReviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
