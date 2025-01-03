package com.tubes.pbw.Artist;

import java.util.List;
import java.util.Optional;

import com.tubes.Data.Artist;

public interface ArtistsRepository {   
    void addArtist(Artist artist) throws Exception;
    Optional<Artist> findArtist(String username);
    Optional<Artist> findByName(String name);
    public List<Artist> searchByName(String query);
    List<Artist> findAllArtists(String query);
    Artist findById(Integer id);
    Artist addNewArtists(String name, String description, String genre, String year, String country, String pathURL) throws Exception;
}