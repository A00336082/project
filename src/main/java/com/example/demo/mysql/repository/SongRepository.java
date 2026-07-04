package com.example.demo.mysql.repository;

import com.example.demo.mysql.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Songs table.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    List<Song> findByArtistArtistId(Integer artistId);

    List<Song> findByTitleContainingIgnoreCase(String title);
}
