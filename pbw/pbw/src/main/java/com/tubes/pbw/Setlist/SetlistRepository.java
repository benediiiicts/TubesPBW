package com.tubes.pbw.Setlist;

import java.util.List;
import java.util.Optional;

import com.tubes.Data.SetList;

public interface SetlistRepository {
    void save(SetList setlist) throws Exception;
    Optional<SetList> findById(String id);
    SetList findById(Integer id);
    List<SetList> findByArtist(Integer id);
    List<SetList> findSetListByShowId(Long id);
}
