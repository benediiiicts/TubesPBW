package com.tubes.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {

    @NotNull
    private String PhotosURL;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    @Size(min = 2, max = 50)
    private String Description;

    @NotNull
    @Size(min = 2, max = 50)
    private String genre;
    
    @NotNull
    @Size(min = 2, max = 50)
    private String Year;
    
    @NotNull
    @Size(min = 2, max = 50)
    private String Country;

    public String toString(){
        return "Artist{" +
                "PhotosURL='" + PhotosURL + '\'' +
                ", name='" + name + '\'' +
                ", Description='" + Description + '\'' +
                ", genre='" + genre + '\'' +
                ", Year='" + Year + '\'' +
                ", Country='" + Country + '\'' +
                '}';
    }
}

