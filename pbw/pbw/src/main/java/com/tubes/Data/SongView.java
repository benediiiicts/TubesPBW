package com.tubes.Data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongView {
    Song song;
    String formattedListener;
    Artist artist;

    public SongView(Song song, String formattedListener){
        this.song = song;
        this.formattedListener = formattedListener;
    }
}
