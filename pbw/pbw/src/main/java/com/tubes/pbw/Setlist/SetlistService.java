package com.tubes.pbw.Setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetlistService {
    @Autowired
    SetlistRepository setlistRepository; 
}
