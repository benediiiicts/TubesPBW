package com.tubes.Data;

import java.sql.Date;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    
    @NotNull
    private int id;

    @NotNull
    private String commentar;

    @NotNull
    private Date date;

    @NotNull
    private int id_setlist;

    @NotNull
    private User user;
}
