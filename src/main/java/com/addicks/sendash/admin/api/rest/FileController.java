package com.addicks.sendash.admin.api.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  @RequestMapping(value = "/zip", method = RequestMethod.GET, produces = {
      "application/octet-stream" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a zip containing scripts of the powershell directory.", notes = "Returns all files in the directory in the zip.")
  public void getZipedScripts(HttpServletRequest request, HttpServletResponse response) {
    log.error("made it to controller");
    try (ZipInputStream fileInputStream = fileService.getZip()) {
      log.error("We have the zip!");
      response.setContentType("application/zip");
      OutputStream outStream = response.getOutputStream();
      IOUtils.copy(fileInputStream, outStream);
      // return
      // ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
      // .body(fileInputStream);
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    log.error("not good!");

  }

}
