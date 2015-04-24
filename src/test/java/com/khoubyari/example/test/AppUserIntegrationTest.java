package com.khoubyari.example.test;

import com.khoubyari.example.Application;
import com.khoubyari.example.dao.jpa.PersonRepository;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit.FlywayTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import org.springframework.data.domain.Pageable;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by ann on 4/23/15.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Profile("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class })
@FlywayTest
public class AppUserIntegrationTest {



    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testFlywayAddingUser() {
        System.out.println("\n\n\n\n***************************\n\tusers:" + personRepository.findAll());
    }


}
