package com.tubes.Data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    private Long id;               // ID untuk venue
    private String name;           // Nama venue
    private String address;        // Alamat venue
    private String address2;       // Alamat tambahan venue
    private String city;           // Kota venue
    private String state;          // Negara bagian venue
    private String zip;            // Kode pos venue
    private Boolean geocodable;    // Apakah venue dapat digeocode
    private Double latitude;       // Latitude venue
    private Double longitude;      // Longitude venue
    private String website;        // Website venue
    private String phone;          // Nomor telepon venue
    private String email;          // Email venue
}

