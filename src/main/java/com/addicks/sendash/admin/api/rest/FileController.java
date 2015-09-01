package com.addicks.sendash.admin.api.rest;

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
@RequestMapping(value = "file-manager")
@Api(value = "status", description = "Status update for health check scripts")
public class FileController {

  @Autowired
  private FileService fileService;

  public FileController() {

  }

  @RequestMapping(value = "/zip", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/zip" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a zip containing scripts of the powershell directory.", notes = "Returns all files in the directory in the zip.")
  public String getZipedScripts() {
    return null;
  }

}
