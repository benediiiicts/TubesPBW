package com.tubes.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Song { 
    private int id_song;
    private int id_album;
    private String title;
    private String url;
    private Long listener;
}
