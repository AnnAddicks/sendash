package com.addicks.sendash.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.domain.properties.RepositoryProperties;

@Service
public class FileService {

  @Autowired
  private RepositoryProperties repositoryProperties;

}
