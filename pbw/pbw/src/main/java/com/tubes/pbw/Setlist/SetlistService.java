package com.tubes.pbw.Setlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubes.Data.SetList;
import com.tubes.pbw.Artist.ArtistsService;
import com.tubes.pbw.Show.ShowsService;

@Service
public class SetlistService {
    @Autowired
    SetlistRepository setlistRepository;

    public SetList getSetList(Integer idSetlist){
        return setlistRepository.findById(idSetlist);
    }
    public List<SetList> getSetListByArtist(Integer idArtist){
        return setlistRepository.findByArtist(idArtist);
    }

    public  List<SetList> getSetlistByShowId(Long id){
        return setlistRepository.findSetListByShowId(id);
    }
    
}
