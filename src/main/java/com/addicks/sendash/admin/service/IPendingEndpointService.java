package com.addicks.sendash.admin.service;

import com.addicks.sendash.admin.domain.PendingEndpoint;

public interface IPendingEndpointService extends ICrudService<PendingEndpoint> {

  void approve(PendingEndpoint endpoint);
}
