package com.example.demo.mysql.repository;

import com.example.demo.mysql.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Artists table.
 */
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    List<Artist> findByGenre(String genre);

    List<Artist> findByNameContainingIgnoreCase(String name);
}
