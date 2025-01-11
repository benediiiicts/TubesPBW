package com.tubes.pbw.Song;

import java.util.List;

import com.tubes.Data.Song;
import com.tubes.Data.SongDetailView;

public interface SongRepository {
    Song findById(Integer id);
    List<Song> findByArtist(Integer idArtist);
    List<Song> findSongsBySetlistId(Integer idSetlist);
    List<Song> findTop5Songs();
    List<Song> searchSongs(String query);
     public List<SongDetailView> searchSongsDetail(String query);
}
