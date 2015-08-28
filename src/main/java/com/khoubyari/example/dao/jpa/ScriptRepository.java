package com.khoubyari.example.dao.jpa;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.khoubyari.example.domain.Script;

/**
 * Created by ann on 5/13/15.
 */
public interface ScriptRepository extends PagingAndSortingRepository<Script, String> {

    public Script findByScriptName(String name);
}
