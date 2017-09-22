package com.joerg.restclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.joerg.restclient.dtos.MyProcessDefinitionDto;

public class ProcessDefinition {
	private String baseAddress = "http://localhost:8080/engine-rest";
	private Client client = null;

	public ProcessDefinition(String baseAddress, Client client) {
		this.baseAddress = baseAddress;
		this.client = client;
	}

	public void startProcessInstance() {
		String processDefinitionId = getProcessDefinitionId();
		WebTarget processDefinitionIdStartTarget = getProcessDefinitionIdStartTarget(processDefinitionId);

		Entity<Object> entity = Entity.entity(null, MediaType.APPLICATION_JSON);

		processDefinitionIdStartTarget //
				.request(MediaType.APPLICATION_JSON_TYPE) //
				.accept(MediaType.APPLICATION_JSON_TYPE) //
				.post(entity);
	}

	public void printProcessDefinitionString() {
		String processDefinitionString = getProcessDefinitionAsString();

		System.out.println(processDefinitionString);
	}

	public void printProcessDefinition() {
		MyProcessDefinitionDto myProcessDefinitionDto = getProcessDefinition();
		System.out.println("Process-Definition:");
		System.out.println("Key: " + myProcessDefinitionDto.getKey());
		System.out.println("Name: " + myProcessDefinitionDto.getName());
		System.out.println("Resource: " + myProcessDefinitionDto.getResource());
	}

	private String getProcessDefinitionId() {
		MyProcessDefinitionDto myProcessDefinitionDto = getProcessDefinition();
		return myProcessDefinitionDto.getId();
	}

	private String getProcessDefinitionAsString() {
		WebTarget processDefinitionKeyProcessTarget = getProcessDefinitionKeyProcessTarget();
		// WebTarget target = client.target(BASE_ADDRESS + "/process-definition/key/process");
		String processDefinitionString = processDefinitionKeyProcessTarget.request(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
				.get(String.class);

		return processDefinitionString;
	}

	private MyProcessDefinitionDto getProcessDefinition() {
		WebTarget processDefinitionKeyProcessTarget = getProcessDefinitionKeyProcessTarget();

		MyProcessDefinitionDto myProcessDefinitionDto = processDefinitionKeyProcessTarget.request(MediaType.APPLICATION_JSON_TYPE)
				.accept(MediaType.APPLICATION_JSON_TYPE).get(MyProcessDefinitionDto.class);

		return myProcessDefinitionDto;
	}

	private WebTarget getProcessDefinitionKeyProcessTarget() {
		WebTarget target = this.client.target(this.baseAddress);
		target = target.path("process-definition");
		target = target.path("key");
		target = target.path("process");

		return target;
	}

	private WebTarget getProcessDefinitionIdStartTarget(String id) {
		WebTarget target = this.client.target(this.baseAddress);
		target = target.path("process-definition");
		target = target.path(id);
		target = target.path("start");
		return target;
	}
}
