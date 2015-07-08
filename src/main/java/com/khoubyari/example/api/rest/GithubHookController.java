package com.khoubyari.example.api.rest;

import com.khoubyari.example.domain.Payload;
import com.khoubyari.example.domain.Status;
import com.khoubyari.example.service.GithubService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ann on 5/8/15.
 */
@RestController
@RequestMapping(value = GithubHookController.REQUEST_MAPPING)
@Api(value = "github", description = "Destination for a github web hook for when a repository has had a push event.")
public class GithubHookController extends AbstractRestHandler {

    public static final String REQUEST_MAPPING = "/github";
    private static final Logger log = LoggerFactory.getLogger(GithubHookController.class);

    @Autowired
    private GithubService githubService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Listens for a github webhook to say an update is needed.", notes = "When a github payload is received, we need to update the powershell scripts. ")
    public void pushEvent(@RequestBody Payload payload, HttpServletRequest request, HttpServletResponse response) {

        log.debug("*******************************************");
        log.debug("Payload: " + payload);
        log.debug("*******************************************");

        payload.setReceivedTimestamp(Calendar.getInstance().getTime());
        githubService.updateGithubData(payload);

    }


    @RequestMapping(value = "",
            method = RequestMethod.GET,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "returns all the payloads from github.", notes = " ")
    public Iterable<Payload> getLog() {

        Iterable<Payload> payloads = githubService.getPayloadHistory();
        log.debug("*******************************************");
        log.debug("Payloads: " + payloads);
        log.debug("*******************************************");

        return payloads;
    }
}
