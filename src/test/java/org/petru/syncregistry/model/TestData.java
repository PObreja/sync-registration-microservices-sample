package org.petru.syncregistry.model;

import org.petru.syncregistry.services.ResponseMessage;
import org.petru.syncregistry.services.SyncRegistryDefinition;

public class TestData {

	private SyncRegistryDefinition requestPayload;
	private ResponseMessage responsePayload;
	private ResponseStatus responseStatus;

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

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

}
