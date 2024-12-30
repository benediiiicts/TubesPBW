package com.tubes.pbw.Venue;

import java.util.List;

import com.tubes.Data.Venue;

public interface VenueRepository {

     public List<Venue> searchByName(String query);
     public List<Venue> findAllVenues(String sql);
     public Venue findById(Long id);
    
}