package com.example.demo.mysql.service;

import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mysql.dto.PlaylistSongDtos.PlaylistSongRequest;
import com.example.demo.mysql.dto.PlaylistSongDtos.PlaylistSongResponse;
import com.example.demo.mysql.entity.Playlist;
import com.example.demo.mysql.entity.PlaylistSong;
import com.example.demo.mysql.entity.Song;
import com.example.demo.mysql.repository.PlaylistRepository;
import com.example.demo.mysql.repository.PlaylistSongRepository;
import com.example.demo.mysql.repository.SongRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Business logic for adding and removing songs from playlists.
 */
@Service
@Transactional
public class PlaylistSongService {

    private final PlaylistSongRepository playlistSongRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public PlaylistSongService(
            PlaylistSongRepository playlistSongRepository,
            PlaylistRepository playlistRepository,
            SongRepository songRepository) {
        this.playlistSongRepository = playlistSongRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @Transactional(readOnly = true)
    public List<PlaylistSongResponse> findAll() {
        return playlistSongRepository.findAll().stream()
                .map(PlaylistSongResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlaylistSongResponse findById(Integer id) {
        return PlaylistSongResponse.from(getPlaylistSongOrThrow(id));
    }

    public PlaylistSongResponse create(PlaylistSongRequest request) {
        validateRequest(request);

        if (playlistSongRepository.existsByPlaylistPlaylistIdAndSongSongId(
                request.playlistId(), request.songId())) {
            throw new ConflictException("Song already exists in this playlist");
        }

        Playlist playlist = playlistRepository.findById(request.playlistId())
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found: " + request.playlistId()));
        Song song = songRepository.findById(request.songId())
                .orElseThrow(() -> new ResourceNotFoundException("Song not found: " + request.songId()));

        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);
        playlistSong.setPosition(request.position());

        return PlaylistSongResponse.from(playlistSongRepository.save(playlistSong));
    }

    public PlaylistSongResponse update(Integer id, PlaylistSongRequest request) {
        validateRequest(request);

        PlaylistSong playlistSong = getPlaylistSongOrThrow(id);
        Playlist playlist = playlistRepository.findById(request.playlistId())
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found: " + request.playlistId()));
        Song song = songRepository.findById(request.songId())
                .orElseThrow(() -> new ResourceNotFoundException("Song not found: " + request.songId()));

        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);
        playlistSong.setPosition(request.position());

        try {
            return PlaylistSongResponse.from(playlistSongRepository.save(playlistSong));
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Song already exists in this playlist");
        }
    }

    public void delete(Integer id) {
        PlaylistSong playlistSong = getPlaylistSongOrThrow(id);
        playlistSongRepository.delete(playlistSong);
    }

    private PlaylistSong getPlaylistSongOrThrow(Integer id) {
        return playlistSongRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist song not found: " + id));
    }

    private void validateRequest(PlaylistSongRequest request) {
        if (request.playlistId() == null) {
            throw new IllegalArgumentException("playlistId is required");
        }
        if (request.songId() == null) {
            throw new IllegalArgumentException("songId is required");
        }
    }
}
