package com.tubes.pbw.Song;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Artist;
import com.tubes.Data.Song;

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
}
