package com.addicks.sendash.admin.service;

import java.util.Collection;

import com.addicks.sendash.admin.domain.PendingEndpoint;

public interface IPendingEndpointService extends ICrudService<PendingEndpoint> {

  void approve(Collection<Long> idsToApprove);
}
