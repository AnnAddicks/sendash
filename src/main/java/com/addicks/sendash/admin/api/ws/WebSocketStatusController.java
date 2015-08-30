package com.addicks.sendash.admin.api.ws;

import org.springframework.stereotype.Controller;

import com.addicks.sendash.admin.domain.Status;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * Created by ann on 4/16/15.
 */

@Controller
public class WebSocketStatusController {

    @MessageMapping("/ws/status")
    @SendTo("/topic/status")
    public Status getUpdateStatus() {

        return new Status(Boolean.TRUE);
    }
}