package com.tubes.pbw.Song;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Artist;
import com.tubes.Data.Song;
import com.tubes.Data.SongDetailView;

@Service
public class SongService {
    
    @Autowired
    SongRepository songRepository;

    public Song getSongById(Integer id){
        return songRepository.findById(id);
    }
    public List<Song> getSongsByArtist(Integer idArtist){
        return songRepository.findByArtist(idArtist);
    }

    public  List<Song> getSongsBySetlistId(Integer id){
        return songRepository.findSongsBySetlistId(id);
    }

    public List<Song> getTop5Songs(){
        return songRepository.findTop5Songs();
    }

    public List<Song> searchSongs(String query){
        return songRepository.searchSongs(query);
    }

    public List<SongDetailView> searchSongsDetail(String query){
        return songRepository.searchSongsDetail(query);
    }
}
