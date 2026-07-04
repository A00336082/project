package com.example.demo.mysql.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mysql.dto.SongDtos.SongRequest;
import com.example.demo.mysql.dto.SongDtos.SongResponse;
import com.example.demo.mysql.entity.Artist;
import com.example.demo.mysql.entity.Song;
import com.example.demo.mysql.repository.ArtistRepository;
import com.example.demo.mysql.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for song catalog operations.
 */
@Service
@Transactional
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongService(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public List<SongResponse> findAll() {
        return songRepository.findAll().stream()
                .map(SongResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public SongResponse findById(Integer id) {
        return SongResponse.from(getSongOrThrow(id));
    }

    public SongResponse create(SongRequest request) {
        validateRequest(request);

        Artist artist = artistRepository.findById(request.artistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found: " + request.artistId()));

        Song song = new Song();
        song.setArtist(artist);
        song.setTitle(request.title());
        song.setDurationSeconds(request.durationSeconds());
        song.setReleasedAt(request.releasedAt());

        return SongResponse.from(songRepository.save(song));
    }

    public SongResponse update(Integer id, SongRequest request) {
        validateRequest(request);

        Song song = getSongOrThrow(id);
        Artist artist = artistRepository.findById(request.artistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found: " + request.artistId()));

        song.setArtist(artist);
        song.setTitle(request.title());
        song.setDurationSeconds(request.durationSeconds());
        song.setReleasedAt(request.releasedAt());

        return SongResponse.from(songRepository.save(song));
    }

    public void delete(Integer id) {
        Song song = getSongOrThrow(id);
        songRepository.delete(song);
    }

    private Song getSongOrThrow(Integer id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found: " + id));
    }

    private void validateRequest(SongRequest request) {
        if (request.artistId() == null) {
            throw new IllegalArgumentException("artistId is required");
        }
        if (request.title() == null || request.title().isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
    }
}
