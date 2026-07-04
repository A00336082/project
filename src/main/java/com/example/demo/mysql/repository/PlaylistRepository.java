package com.example.demo.mysql.repository;

import com.example.demo.mysql.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Playlists table.
 * Also exposes Get_Top_Playlists via PlaylistRepositoryCustom.
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer>, PlaylistRepositoryCustom {

    List<Playlist> findByUserUserId(Integer userId);

    List<Playlist> findByNameContainingIgnoreCase(String name);
}
