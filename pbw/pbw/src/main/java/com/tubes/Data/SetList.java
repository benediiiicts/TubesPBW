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

    private Integer id;

    private String title;

    private Show show;
    
    private Artist artist;
    
    private List<Song> songs;

}


