package com.khoubyari.example.api.rest;

import com.khoubyari.example.domain.EndpointStatus;
import com.khoubyari.example.domain.Hotel;
import com.khoubyari.example.domain.Status;
import com.khoubyari.example.service.StatusService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ann on 4/16/15.
 */

@RestController
@RequestMapping(value = "/status")
@Api(value = "status", description = "Status update for health check scripts")
public class StatusController  extends AbstractRestHandler {

    @Autowired
    StatusService statusService;

    @RequestMapping(value = "/stub",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Stub that mocks if an update is needed.", notes = "Returns the Status of the scripts:  update or no update.")
    public @ResponseBody
    Status checkStatusStub(@RequestHeader(value = "API_KEY") String apiKey, HttpServletRequest request, HttpServletResponse response) {

        if(apiKey.equalsIgnoreCase("update")) {
            return new Status(Boolean.TRUE);
        }

        return new Status(Boolean.FALSE);
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Check if an update is needed.", notes = "Returns the Status of the scripts:  update or no update.")
    public @ResponseBody
    Status checkStatus(@RequestBody EndpointStatus endpointStatus, HttpServletRequest request, HttpServletResponse response) {

        return statusService.getStatus(endpointStatus);




    }



}
