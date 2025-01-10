package com.tubes.Data;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArtistReport {
    private String artistName;
    private Long totalListeners;
}
