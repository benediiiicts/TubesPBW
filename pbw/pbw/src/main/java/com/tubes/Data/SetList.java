package com.tubes.Data;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetList {
    private Artist artist;

    private String venue;

    private LocalDate date;

    private List<Song> songs;



    public void setDateNow(){
        this.date = LocalDate.now();
    }

    public void setDateYesterday(){
        this.date = LocalDate.now().minusDays(1);
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void addSong(Song song){
        this.songs.add(song);
    }
}


