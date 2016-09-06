package org.petru.syncregistry.services;

import org.apache.camel.component.seda.SedaEndpoint;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gytheio.messaging.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncRegistryController
{
	private static final Log logger = LogFactory.getLog(SyncRegistryController.class);

	@Autowired
	private MessageProducer messageProducer;

	@Autowired
	private SpringCamelContext springCamelContext;

	@RequestMapping(method = RequestMethod.POST, path = "/syncregistry")
	public ResponseMessage addSyncRegistryDefinition(
			@RequestBody SyncRegistryDefinition definition) {
		ResponseMessage responseMessage = new ResponseMessage(
				"Registered sync definition for "
						+ definition.getFirstSystemId() + ":"
						+ definition.getFirstSystemItem() + " <-> "
						+ definition.getSecondSystemId() + ":"
						+ definition.getSecondSystemItem());
		logger.info(responseMessage.getMessage());
		messageProducer.send(definition);
		return responseMessage;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/que") public Integer getQueSize()
	{
		SedaEndpoint sedaEndpoint = (SedaEndpoint) springCamelContext
			.getEndpoint("seda:integrations");
		return sedaEndpoint.getCurrentQueueSize();
	}
}
