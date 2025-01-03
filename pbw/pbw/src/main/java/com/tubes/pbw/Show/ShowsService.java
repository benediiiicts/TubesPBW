package com.tubes.pbw.Show;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.Show;

@Service
public class ShowsService {
    
    @Autowired
    ShowsRepository showsRepository;

    public Show getShow(Long id){
        return showsRepository.findById(id);
    }
}
