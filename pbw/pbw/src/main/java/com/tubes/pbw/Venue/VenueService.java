package com.tubes.pbw.Venue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Venue;

@Service
public class VenueService {
    
    @Autowired
    private VenueRepository venueRepository;

    public Venue getVenueById(Long id){
        return venueRepository.findById(id);
    }

    public List<Venue> getVenueByName(String name){
        return venueRepository.searchByName(name);
    }

}
