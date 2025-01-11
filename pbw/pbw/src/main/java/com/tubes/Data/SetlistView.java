package com.tubes.Data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SetlistView {
    private SetList setlist;
    private Show show;
}
