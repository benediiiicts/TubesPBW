package com.tubes.Data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongDetailView {
    private Integer id_song;
    private String title;
    private String url;
    private Long listener;
    private Integer artistId;
    private String artistName;
}