package com.vmw.assignment.ng.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.services.pubsub.model.PubsubMessage;

/**
 * @author adarsh
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PubsubMessageWrapper implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Message content published by the pubsub
	 */
	PubsubMessage message;
	/**
	 * subscription details of PubSub
	 */
	String subscription;

	public PubsubMessage getMessage() {
		return message;
	}

	public void setMessage(PubsubMessage message) {
		this.message = message;
	}

	public String getSubscription() {
		return subscription;
	}

	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
