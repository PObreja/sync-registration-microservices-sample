package org.petru.syncregistry.model;

import org.petru.syncregistry.services.ResponseMessage;
import org.petru.syncregistry.services.SyncRegistryDefinition;

public class TestData {
	private SyncRegistryDefinition requestPayload;
	private ResponseMessage responsePayload;

	public SyncRegistryDefinition getRequestPayload() {
		return requestPayload;
	}

	public void setRequestPayload(SyncRegistryDefinition requestPayload) {
		this.requestPayload = requestPayload;
	}

	public ResponseMessage getResponsePayload() {
		return responsePayload;
	}

	public void setResponsePayload(ResponseMessage responsePayload) {
		this.responsePayload = responsePayload;
	}

}
