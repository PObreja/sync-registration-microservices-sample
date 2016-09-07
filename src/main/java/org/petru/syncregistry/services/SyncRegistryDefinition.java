package org.petru.syncregistry.services;

import java.io.Serializable;

public class SyncRegistryDefinition implements Serializable
{
	private String firstSystemId;
	private String firstSystemItem;
	private String secondSystemId;
	private String secondSystemItem;

	public String getFirstSystemId() {
		return firstSystemId;
	}

	public void setFirstSystemId(String firstSystemId) {
		this.firstSystemId = firstSystemId;
	}

	public String getFirstSystemItem() {
		return firstSystemItem;
	}

	public void setFirstSystemItem(String firstSystemItem) {
		this.firstSystemItem = firstSystemItem;
	}

	public String getSecondSystemId() {
		return secondSystemId;
	}

	public void setSecondSystemId(String secondSystemId) {
		this.secondSystemId = secondSystemId;
	}

	public String getSecondSystemItem() {
		return secondSystemItem;
	}

	public void setSecondSystemItem(String secondSystemItem) {
		this.secondSystemItem = secondSystemItem;
	}

}
