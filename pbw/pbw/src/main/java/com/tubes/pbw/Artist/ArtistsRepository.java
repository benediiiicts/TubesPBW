package com.tubes.pbw.Artist;

import java.util.Optional;

import com.tubes.Data.Artist;

public interface ArtistsRepository {   
    void addArtist(Artist artist) throws Exception;
    Optional<Artist> findArtist(String username);
}