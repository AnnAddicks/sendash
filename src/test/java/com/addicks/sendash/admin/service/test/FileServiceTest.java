package com.addicks.sendash.admin.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.addicks.sendash.admin.Application;
import com.addicks.sendash.admin.domain.properties.RepositoryProperties;
import com.addicks.sendash.admin.service.FileService;

@Profile("test")
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class FileServiceTest {

  private static final Logger log = LoggerFactory.getLogger(FileServiceTest.class);

  @Autowired
  private FileService fileService;

  @Autowired
  private RepositoryProperties repositoryProperties;

  @Test
  public void shouldCreateZip() {
    try {
      Path destFile = Paths.get(repositoryProperties.getZipOfRep() + FileService.ZIP_NAME);
      Files.deleteIfExists(destFile);
    }
    catch (java.nio.file.FileSystemNotFoundException | IOException e) {
    }

    fileService.createZip();

    File zipFile = new File(repositoryProperties.getZipOfRep() + FileService.ZIP_NAME);
    assertTrue(zipFile.exists());

  }

  @Test
  public void shouldDeleteThenCreateZip() {
    fileService.createZip();
    fileService.createZip();

    File zipFile = new File("./" + FileService.ZIP_NAME);
    assertTrue(zipFile.exists());
  }

  @Test
  public void shouldReturnZip() {

    try (ZipInputStream zis = fileService.getZip()) {
      assertNotNull(zis);
      assertNotNull(zis.getNextEntry());
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  // TODO extract zip out and compare the directories.
}
