package com.example.demo.mysql.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mysql.dto.PlaylistDtos.PlaylistRequest;
import com.example.demo.mysql.dto.PlaylistDtos.PlaylistResponse;
import com.example.demo.mysql.dto.PlaylistDtos.TopPlaylistResponse;
import com.example.demo.mysql.entity.Playlist;
import com.example.demo.mysql.entity.User;
import com.example.demo.mysql.repository.PlaylistRepository;
import com.example.demo.mysql.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for playlist operations, including the Get_Top_Playlists stored procedure.
 */
@Service
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public PlaylistService(PlaylistRepository playlistRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<PlaylistResponse> findAll() {
        return playlistRepository.findAll().stream()
                .map(PlaylistResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlaylistResponse findById(Integer id) {
        return PlaylistResponse.from(getPlaylistOrThrow(id));
    }

    /**
     * Invokes the Get_Top_Playlists MySQL stored procedure.
     */
    @Transactional(readOnly = true)
    public List<TopPlaylistResponse> getTopPlaylists(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be greater than 0");
        }
        return playlistRepository.getTopPlaylists(limit).stream()
                .map(TopPlaylistResponse::from)
                .toList();
    }

    public PlaylistResponse create(PlaylistRequest request) {
        validateRequest(request);

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.userId()));

        Playlist playlist = new Playlist();
        playlist.setUser(user);
        playlist.setName(request.name());
        playlist.setDescription(request.description());

        return PlaylistResponse.from(playlistRepository.save(playlist));
    }

    public PlaylistResponse update(Integer id, PlaylistRequest request) {
        validateRequest(request);

        Playlist playlist = getPlaylistOrThrow(id);
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.userId()));

        playlist.setUser(user);
        playlist.setName(request.name());
        playlist.setDescription(request.description());

        return PlaylistResponse.from(playlistRepository.save(playlist));
    }

    public void delete(Integer id) {
        Playlist playlist = getPlaylistOrThrow(id);
        playlistRepository.delete(playlist);
    }

    private Playlist getPlaylistOrThrow(Integer id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found: " + id));
    }

    private void validateRequest(PlaylistRequest request) {
        if (request.userId() == null) {
            throw new IllegalArgumentException("userId is required");
        }
        if (request.name() == null || request.name().isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
    }
}
