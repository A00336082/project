package com.example.demo.mysql.repository;

import com.example.demo.mysql.entity.Playlist;
import com.example.demo.mysql.projection.TopPlaylistView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public class PlaylistRepositoryCustomImpl implements PlaylistRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<TopPlaylistView> getTopPlaylists(int limitCount) {
        Query query = entityManager.createNativeQuery("CALL Get_Top_Playlists(:limitCount)");
        query.setParameter("limitCount", limitCount);

        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(this::mapRow)
                .toList();
    }

    private TopPlaylistView mapRow(Object[] row) {
        return new TopPlaylistView(
                ((Number) row[0]).intValue(),
                ((Number) row[1]).intValue(),
                (String) row[2],
                (String) row[3],
                toLocalDateTime(row[4]),
                (String) row[5],
                ((Number) row[6]).longValue()
        );
    }

    private LocalDateTime toLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime localDateTime) {
            return localDateTime;
        }
        if (value instanceof Timestamp timestamp) {
            return timestamp.toLocalDateTime();
        }
        throw new IllegalArgumentException("Unexpected timestamp type: " + value.getClass());
    }
}
