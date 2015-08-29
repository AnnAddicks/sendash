package com.addicks.sendash.admin.api.rest;

import com.wordnik.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ann on 5/20/15.
 */
@RestController
@RequestMapping(value = "/scripts")
@Api(value = "scripts", description = "Script hosting API")
public class PowershellController extends AbstractRestHandler {

    //TODO add uri to host powershell file(s)
}
