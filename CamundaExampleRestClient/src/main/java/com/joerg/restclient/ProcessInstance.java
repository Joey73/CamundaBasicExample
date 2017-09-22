package com.joerg.restclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ProcessInstance {
	private String baseAddress = null;
	private Client client = null;

	public ProcessInstance(String baseAddress, Client client) {
		this.baseAddress = baseAddress;
		this.client = client;
	}
	
	public void deleteProcessInstance(String id){
		WebTarget target = getProcessInstance(id);
		Response response = target //
				.request(MediaType.APPLICATION_JSON_TYPE) //
				.accept(MediaType.APPLICATION_JSON_TYPE) //
				.delete();
		System.out.println("ProcessInstance with ID " + id + " deleted - Status " + response.getStatus());
	}

	private WebTarget getProcessInstance(String id){
		WebTarget target = client.target(this.baseAddress);
		target = target.path("process-instance");
		target = target.path(id);
		return target;
	}
}
