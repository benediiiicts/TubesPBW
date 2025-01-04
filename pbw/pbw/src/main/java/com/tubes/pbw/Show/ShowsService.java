package com.tubes.pbw.Show;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Show;

@Service
public class ShowsService {

    @Autowired
    private jdbcShowsRepository jdbcshowsRepository;

    public void addNewShow(String showName, String date, String venue, String description) throws ParseException {
        Date convertedDate = convertStringToSqlDate(date) ;
        Show show = new Show();
        show.setShowName(showName);
        show.setDate(convertedDate);
        show.setVenue(venue);
        show.setDescription(description);
        try {
            jdbcshowsRepository.addShow(show);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Date convertStringToSqlDate(String dateString) throws ParseException {
        // Format tanggal yang sesuai dengan input
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Parsing String menjadi java.util.Date
        java.util.Date utilDate = dateFormat.parse(dateString);
        // Konversi dari java.util.Date ke java.sql.Date
        return new Date(utilDate.getTime());
    }
}
