package com.tubes.pbw.Setlist;

import java.util.Optional;
import java.util.Set;

import com.tubes.Data.SetList;

public interface SetlistRepository {
    void save(SetList setlist) throws Exception;
    Optional<SetList> findById(String id);
    SetList findById(Integer id);
}
