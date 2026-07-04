package com.example.demo.mysql.repository;

import com.example.demo.mysql.entity.PlaylistSong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the PlaylistSongs junction table.
 */
@Repository
public interface PlaylistSongRepository extends JpaRepository<PlaylistSong, Integer> {

    List<PlaylistSong> findByPlaylistPlaylistIdOrderByPositionAsc(Integer playlistId);

    Optional<PlaylistSong> findByPlaylistPlaylistIdAndSongSongId(Integer playlistId, Integer songId);

    boolean existsByPlaylistPlaylistIdAndSongSongId(Integer playlistId, Integer songId);
}
