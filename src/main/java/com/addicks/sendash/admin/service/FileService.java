package com.addicks.sendash.admin.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.domain.properties.RepositoryProperties;

@Service
public class FileService {

  @Autowired
  private RepositoryProperties repositoryProperties;

  private static final Logger log = LoggerFactory.getLogger(FileService.class);

  public FileService() {

  }

  private List<String> generateFileList(String sourceFolder, File node) throws IOException {
    List<String> fileList = new ArrayList<String>();
    // add file only
    if (node.isFile()) {
      fileList.add(node.getCanonicalPath());

    }

    if (node.isDirectory()) {
      String[] subNote = node.list();
      for (String filename : subNote) {
        if (!filename.startsWith(".")) {
          fileList.addAll(generateFileList(sourceFolder, new File(node, filename)));
        }
      }
    }
    return fileList;
  }

  public void createZip() {
    String zipFile = repositoryProperties.getZipOfRep();
    String sourceDirectory = repositoryProperties.getLocalRepo();
    byte[] buffer = new byte[1024];

    try {
      // create object of FileOutputStream
      FileOutputStream fout = new FileOutputStream(zipFile);

      // create object of ZipOutputStream from FileOutputStream
      try (ZipOutputStream zout = new ZipOutputStream(fout)) {

        // create File object from directory name
        File dir = new File(sourceDirectory);
        log.debug("DIR: " + dir);

        // check to see if this directory exists
        if (!dir.isDirectory()) {
          log.error(sourceDirectory + " is not a directory");
        }
        else {
          List<String> files = generateFileList(sourceDirectory, dir);
          log.error("*************");
          String startingDir = repositoryProperties.getZipOfRep();
          for (String fileName : files) {

            // create object of FileInputStream for source file
            try {

              log.error("FileName: " + fileName);
              log.error("Starting Dir: " + startingDir);
              FileInputStream fin = new FileInputStream(fileName);
              zout.putNextEntry(new ZipEntry(fileName.replace(startingDir, "")));
              log.error("substring" + fileName.replace(startingDir, ""));

              /*
               * After creating entry in the zip file, actually write the file.
               */
              int length;

              while ((length = fin.read(buffer)) > 0) {
                zout.write(buffer, 0, length);
              }
              zout.closeEntry();
              fin.close();
            }
            catch (FileNotFoundException ex) {
              log.error("File not Found:" + fileName);
            }
          }
        }
        log.error("*************");
        // close the ZipOutputStream
        zout.close();

        log.debug("Zip file has been created!");

      }
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }

  }

  public ZipInputStream getZip() throws IOException {
    File zipFile = new File(repositoryProperties.getZipOfRep());

    return new ZipInputStream(new FileInputStream(zipFile));
  }

}
