package com.tubes.pbw.Song;

import java.util.List;

import com.tubes.Data.Song;

public interface SongRepository {
    Song findById(Integer id);
    List<Song> findByArtist(Integer idArtist);
}
