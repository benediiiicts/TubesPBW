package com.tubes.pbw.Artist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Artist;

@Service
public class ArtistsService {

    @Autowired
    private JdbcArtistsRepository artistRepository;

    public Artist addNewArtists(String name, String description, String genre, String year, String country, String pathURL){
        Artist artist;
        try{
            artist = artistRepository.addNewArtists(name, description, genre, year, country, pathURL);
        }catch(Exception e){
            return null;
        }
        return artist;
    }

    public List<Artist> searchArtists(String query) {
        return artistRepository.searchByName(query);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findToGetAllArtists();
    }

    public List<Artist> getArtistsByPage(int page, int size) {
        int offset = (page - 1) * size;
        return artistRepository.findArtistsByPage(size, offset);
    }
    
    public int getTotalPages(int size, String search, String country, String genre) {
        return artistRepository.findTotalPages(size, search, country, genre);
    }

    public Artist getArtistById(Integer id){
        return artistRepository.findById(id);
    }

    public List<Artist> getFilteredArtists(int page, int size, String search, String country, String genre) {
        return artistRepository.findFilteredArtists(page, size, search, country, genre);
    }
    
    public List<String> getAllCountries() {
        return artistRepository.findAllCountries();
    }

    public List<String> getAllGenres() {
        return artistRepository.findAllGenres();
    }      
    
}