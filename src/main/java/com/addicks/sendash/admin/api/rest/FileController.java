package com.addicks.sendash.admin.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = "file-manager")
@Api(value = "status", description = "Status update for health check scripts")
public class FileController {

}
