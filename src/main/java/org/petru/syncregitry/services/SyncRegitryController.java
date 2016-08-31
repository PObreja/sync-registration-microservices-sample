package org.petru.syncregitry.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncRegitryController {
	
	private static final Log logger = LogFactory.getLog(SyncRegitryController.class);

	@RequestMapping(method = RequestMethod.POST, path = "/syncregistry")
	public ResponseMessage addSyncRegistryDefinition(
			@RequestBody SyncRegistryDefinition definition) {
		ResponseMessage responseMessage = new ResponseMessage("Registered sync definition for "
				+ definition.getFirstSystemId() + ":"
				+ definition.getFirstSystemItem() + " <-> "
				+ definition.getSecondSystemId() + ":"
				+ definition.getSecondSystemItem());
		logger.info(responseMessage.getMessage());
		return responseMessage;
	}

}
