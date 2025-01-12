package com.tubes.pbw.Setlist;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tubes.Data.SetList;
import com.tubes.Data.SetlistHistory;
import com.tubes.pbw.Artist.ArtistsService;
import com.tubes.pbw.Show.ShowsService;

@Service
public class SetlistService {
    @Autowired
    SetlistRepository setlistRepository;

    public SetList getSetList(Integer idSetlist) {
        return setlistRepository.findById(idSetlist);
    }

    public List<SetList> getSetListByArtist(Integer idArtist) {
        return setlistRepository.findByArtist(idArtist);
    }

    public List<SetlistHistory> getSetlistHistories(Integer idsetlist){
        return setlistRepository.getHistory(idsetlist);
    }

    public List<SetList> getAllSetlists() {
        return setlistRepository.findAll();
    }    

    public List<SetList> searchSetlists(String query) {
        return setlistRepository.searchSetlists(query.toLowerCase());
    }

    public  List<SetList> getSetlistByShowId(Long id){
        return setlistRepository.findSetListByShowId(id);
    }

    public Integer addSetlist(String setlistTitle, Long showId, Long artistId){
        if(setlistTitle == null || showId == null || artistId == null){
            return 0;
        }
        return setlistRepository.save(setlistTitle, showId, artistId);
    }

    public List<SetList> get5RandomSetlist(){
        return setlistRepository.find5RandomSetlist();
    }
    
    public void updateSetlist(Integer id, String title, String removedSongsJson, 
    String addedSongsJson, String oldTitle, String editorEmail) {
    
    ObjectMapper mapper = new ObjectMapper();
    try {
        List<Integer> removedSongs = mapper.readValue(removedSongsJson, 
            new TypeReference<List<Integer>>() {});
        List<Map<String, Object>> addedSongs = mapper.readValue(addedSongsJson, 
            new TypeReference<List<Map<String, Object>>>() {});
            
        setlistRepository.updateSetlist(id, title, removedSongs, addedSongs, 
            oldTitle, editorEmail);
    } catch (Exception e) {
        throw new RuntimeException("Error processing song data", e);
    }
}

}

