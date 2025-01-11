package com.tubes.Data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SongDetailView {
    int songId;
    String songTitle;
    String artistName;
    int artistId;
    String albumTitle;
}
