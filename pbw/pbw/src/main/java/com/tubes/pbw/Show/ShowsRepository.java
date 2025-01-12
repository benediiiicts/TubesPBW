package com.tubes.pbw.Show;

import java.util.List;
import java.util.Optional;

import com.tubes.Data.Artist;
import com.tubes.Data.Show;

public interface ShowsRepository {
    Optional<Show> findByName(String showName);

    Integer addShow(Show show) throws Exception;

    Show findById(Long id);

    List<Artist> artistInShow(Long showId);

    List<Show> findByQuery(String query);

    List<Show> find5RandomShows();

    List<Show> findUpcomingShows();

    List<Show> findByNameContaining(String name);


}