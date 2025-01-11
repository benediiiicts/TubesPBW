package com.tubes.pbw.Artist;

import java.util.List;
import java.util.Optional;

import com.tubes.Data.Artist;

public interface ArtistsRepository {   
    void addArtist(Artist artist) throws Exception;
    Optional<Artist> findArtist(String username);
    Optional<Artist> findByName(String name);
    List<Artist> searchByName(String query);
    List<Artist> findAllArtists(String query);
    Artist findById(Integer id);
    Artist addNewArtists(String name, String description, String genre, String year, String country, String pathURL) throws Exception;
    int findTotalPages(int size, String search, String country, String genre);
    List<Artist> findFilteredArtists(int page, int size, String search, String country, String genre);
    List<String> findAllCountries();
    List<String> findAllGenres();
}