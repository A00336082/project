package com.example.demo.mysql.repository;

import com.example.demo.mysql.projection.TopPlaylistView;

import java.util.List;


public interface PlaylistRepositoryCustom {

    /**
     * Calls the Get_Top_Playlists stored procedure to rank playlists by song count.
     *
     * @param limitCount maximum number of playlists to return
     */
    List<TopPlaylistView> getTopPlaylists(int limitCount);
}
