package com.tubes.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Show {
    private Long idShow;           // ID untuk show
    private String showName;       // Nama show
    private Date date;             // Tanggal show
    private Long venue;          // Venue id
    private String description;     // Deskripsi show
}

