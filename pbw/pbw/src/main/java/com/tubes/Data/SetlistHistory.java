package com.tubes.Data;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetlistHistory {
    
    @NotNull
    private Integer id;
    
    @NotNull
    private Integer setlistId;

    @NotNull
    private String email;

    @NotNull
    private Timestamp date;

    @NotNull
    private String activity;
}
