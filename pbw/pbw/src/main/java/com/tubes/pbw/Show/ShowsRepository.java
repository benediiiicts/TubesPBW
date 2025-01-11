package com.tubes.pbw.Show;

import java.util.List;
import java.util.Optional;

import com.tubes.Data.Artist;
import com.tubes.Data.Show;

public interface ShowsRepository {
    Optional<Show> findByName(String showName);
    void addShow(Show show) throws Exception;
    Show findById(Long id);
    List<Artist> artistInShow(Long showId);
    List<Show> find5RandomShows();
    List<Show> findUpcomingShows();
    
}