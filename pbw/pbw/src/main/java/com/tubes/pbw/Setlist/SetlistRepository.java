package com.tubes.pbw.Setlist;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tubes.Data.SetList;
import com.tubes.Data.SetlistHistory;

public interface SetlistRepository {
    void save(SetList setlist) throws Exception;
    Optional<SetList> findById(String id);
    SetList findById(Integer id);
    List<SetList> findByArtist(Integer id);
    List<SetList> findAll();
    List<SetList> searchSetlists(String query);
    List<SetList> findSetListByShowId(Long id);
    List<SetList> find5RandomSetlist();
    Integer save(String setlistTitle, Long showId, Long artistId);
    void updateSetlist(Integer id, String title, List<Integer> removedSongs, 
    List<Map<String, Object>> addedSongs, String oldTitle, String editorEmail);
    List<SetlistHistory> getHistory(Integer idSetlist);
}
