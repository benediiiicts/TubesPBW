package com.tubes.pbw.Artist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Artist;

@Service
public class ArtistsService {

    @Autowired
    private JdbcArtistsRepository artistRepository;

    public List<Artist> searchArtists(String query) {
        return artistRepository.searchByName(query);
    }
}

