package com.khoubyari.example.dao.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.khoubyari.example.domain.Person;
/**
 * Created by ann on 4/23/15.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}