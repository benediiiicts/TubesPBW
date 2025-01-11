package com.tubes.pbw.Show;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Artist;
import com.tubes.Data.Show;

@Service
public class ShowsService {

    @Autowired
    private ShowsRepository showsRepository;

    public List<Show> searchShows(String query){
        return showsRepository.findByQuery(query);
    }

    public Integer addNewShow(String showName, String date, String description, Long venue) throws ParseException {
        Date convertedDate = convertStringToSqlDate(date) ;
        Show show = new Show();
        show.setShowName(showName);
        show.setDate(convertedDate);
        show.setDescription(description);
        show.setVenue(venue);
        try {
            return showsRepository.addShow(show);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Show> getUpcomingShows(){
        return showsRepository.findUpcomingShows();
    }
    
    public static Date convertStringToSqlDate(String dateString) throws ParseException {
        // Format tanggal yang sesuai dengan input
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Parsing String menjadi java.util.Date
        java.util.Date utilDate = dateFormat.parse(dateString);
        // Konversi dari java.util.Date ke java.sql.Date
        return new Date(utilDate.getTime());
    }

    public Show getShow(Long id){
        return showsRepository.findById(id);
    }

    public List<Show> get5RandomShows(){
        return showsRepository.find5RandomShows();
    }

    public List<Artist> artistInShow(Long showId){
        return showsRepository.artistInShow(showId);
    }
}
