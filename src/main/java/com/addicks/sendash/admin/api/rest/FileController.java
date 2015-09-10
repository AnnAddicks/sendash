package com.addicks.sendash.admin.api.rest;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.service.FileService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = FileController.REQUEST_MAPPING)
@Api(value = "status", description = "Status update for health check scripts")
public class FileController {
  public static final String REQUEST_MAPPING = "/file-manager";

  private static final Logger log = LoggerFactory.getLogger(FileController.class);

  @Autowired
  private FileService fileService;

  public FileController() {

  }

  @RequestMapping(value = "/zip", method = RequestMethod.GET, produces = { "application/zip" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a zip containing scripts of the powershell directory.", notes = "Returns all files in the directory in the zip.")
  public void getZipedScripts(HttpServletRequest request, HttpServletResponse response) {
    try (ZipInputStream fileInputStream = fileService.getZip()) {
      // todo remove when done testing, not closing streams!
      log.error("next Entry" + fileService.getZip().getNextEntry());
      String headerKey = "Content-Disposition";
      String headerValue = String.format("attachment; filename=\"sendash\"");
      response.setHeader(headerKey, headerValue);
      response.setContentType("application/zip");

      IOUtils.copy(fileInputStream, response.getOutputStream());

      response.flushBuffer();
      fileInputStream.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping(value = "/zip2", method = RequestMethod.GET, produces = { "application/zip" })
  @ResponseStatus(HttpStatus.OK)
  public FileSystemResource getFile() {
    String headerKey = "Content-Disposition";
    String headerValue = String.format("attachment; filename=\"sendash\"");
    File file = new File("./sendash.zip");
    return new FileSystemResource(file);
  }

}
