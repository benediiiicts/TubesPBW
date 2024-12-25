package com.tubes.Setlist;

import java.util.Optional;

import com.tubes.Data.SetList;

public interface SetlistRepository {
    void save(SetList setlist) throws Exception;
    Optional<SetList> findById(String id);
}
