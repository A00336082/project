package com.example.demo.mysql.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mysql.dto.ArtistDtos.ArtistRequest;
import com.example.demo.mysql.dto.ArtistDtos.ArtistResponse;
import com.example.demo.mysql.entity.Artist;
import com.example.demo.mysql.repository.ArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for artist catalog operations.
 */
@Service
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public List<ArtistResponse> findAll() {
        return artistRepository.findAll().stream()
                .map(ArtistResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ArtistResponse findById(Integer id) {
        return ArtistResponse.from(getArtistOrThrow(id));
    }

    public ArtistResponse create(ArtistRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }

        Artist artist = new Artist();
        artist.setName(request.name());
        artist.setGenre(request.genre());
        artist.setBio(request.bio());

        return ArtistResponse.from(artistRepository.save(artist));
    }

    public ArtistResponse update(Integer id, ArtistRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }

        Artist artist = getArtistOrThrow(id);
        artist.setName(request.name());
        artist.setGenre(request.genre());
        artist.setBio(request.bio());

        return ArtistResponse.from(artistRepository.save(artist));
    }

    public void delete(Integer id) {
        Artist artist = getArtistOrThrow(id);
        artistRepository.delete(artist);
    }

    private Artist getArtistOrThrow(Integer id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found: " + id));
    }
}
