package com.addicks.sendash.admin.service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.domain.properties.RepositoryProperties;

@Service
public class FileService {

  @Autowired
  private RepositoryProperties repositoryProperties;

  private static final String zipName = "sendash.zip";

  public FileService() {

  }

  private FileSystem createZipFileSystem(String zipFilename, boolean create) throws IOException {
    // convert the filename to a URI
    final Path path = Paths.get(zipFilename);
    final URI uri = URI.create("jar:file:" + path.toUri().getPath());

    final Map<String, String> env = new HashMap<>();
    if (create) {
      env.put("create", "true");
    }
    return FileSystems.newFileSystem(uri, env);
  }

  public void createZip() {
    try (FileSystem zipFileSystem = createZipFileSystem(zipName, true)) {
      final Path destDir = Paths.get("./");
      // if the destination doesn't exist, create it
      if (Files.notExists(destDir)) {
        System.out.println(destDir + " does not exist. Creating...");
        Files.createDirectories(destDir);
      }

      // if the zip exists delete it
      try {
        Path destFile = Paths.get(destDir + zipName);
        Files.deleteIfExists(destFile);
      }
      catch (java.nio.file.FileSystemNotFoundException e) {
      }

      final Path root = zipFileSystem.getPath(repositoryProperties.getLocalRepo());

      // walk the zip file tree and copy files to the destination
      Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          final Path destFile = Paths.get(destDir.toString(), file.toString());
          System.out.printf("Extracting file %s to %s\n", file, destFile);
          Files.copy(file, destFile, StandardCopyOption.REPLACE_EXISTING);
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
            throws IOException {
          final Path dirToCreate = Paths.get(destDir.toString(), dir.toString());
          if (Files.notExists(dirToCreate)) {
            System.out.printf("Creating directory %s\n", dirToCreate);
            Files.createDirectory(dirToCreate);
          }
          return FileVisitResult.CONTINUE;
        }
      });
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // public FileSystem getZip() {
  //
  // }

}
