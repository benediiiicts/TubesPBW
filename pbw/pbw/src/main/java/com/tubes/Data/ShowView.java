package com.tubes.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowView {
    private Show show;
    private Venue venue;
    private Artist artist;
}
