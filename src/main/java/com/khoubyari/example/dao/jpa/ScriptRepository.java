package com.khoubyari.example.dao.jpa;

import com.khoubyari.example.domain.Hotel;
import com.khoubyari.example.domain.Script;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ann on 5/13/15.
 */
public interface ScriptRepository extends PagingAndSortingRepository<Script, String> {

    public Script findByScriptName(String name);
}
