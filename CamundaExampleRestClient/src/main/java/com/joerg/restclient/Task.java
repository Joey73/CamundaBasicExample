package com.joerg.restclient;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.joerg.restclient.dtos.MyTaskDto;

public class Task {
	private String baseAddress = null;
	private Client client = null;

	public Task(String baseAddress, Client client) {
		this.baseAddress = baseAddress;
		this.client = client;
	}

	public void completeAllTasks() {
		List<MyTaskDto> myTaskDtoList = getTaskList();
		for (MyTaskDto myTaskDto : myTaskDtoList) {
			completeTask(myTaskDto.getId());
		}
	}

	public void completeTask(String id) {
		WebTarget taskIdClaimTarget = getTaskIdCompleteTarget(id);

		String variables = "{\"variables\":";
		variables += "{\"FormFieldX\": {\"value\": 7}}";
		variables += "}";

		System.out.println("\nVariables-String for completing task: " + variables);

		Entity<String> entity = Entity.entity(variables, MediaType.APPLICATION_JSON);
		Response response = taskIdClaimTarget //
				.request(MediaType.APPLICATION_JSON_TYPE) //
				.accept(MediaType.APPLICATION_JSON_TYPE) //
				.post(entity);

		System.out.println("Task with ID \"" + id + "\" completed - Status: " + response.getStatus());
	}

	public void claimAllTasks() {
		List<MyTaskDto> myTaskDtoList = getTaskList();
		for (MyTaskDto myTaskDto : myTaskDtoList) {
			claimTask(myTaskDto.getId());
		}
	}

	public void claimTask(String id) {
		WebTarget taskIdClaimTarget = getTaskIdClaimTarget(id);

		Entity<String> entity = Entity.entity("{\"userId\": \"demo\"}", MediaType.APPLICATION_JSON);
		Response response = taskIdClaimTarget //
				.request(MediaType.APPLICATION_JSON_TYPE) //
				.accept(MediaType.APPLICATION_JSON_TYPE) //
				.post(entity);

		System.out.println("\nTask with ID \"" + id + "\" claimed - Status: " + response.getStatus());
	}

	public void printTaskString() {
		System.out.println("\n" + getTaskAsString());
	}

	public void printTaskList() {
		List<MyTaskDto> myTaskDtoList = getTaskList();
		if (myTaskDtoList.isEmpty()) {
			System.out.println("\nTasklist is empty.");
			return;
		}
		System.out.println("\nTasklist:");
		for (MyTaskDto myTaskDto : myTaskDtoList) {
			System.out.println("\nID: " + myTaskDto.getId());
			System.out.println("Name: " + myTaskDto.getName());
			System.out.println("Assignee: " + myTaskDto.getAssignee());
			System.out.println("TaskDefinitionKey: " + myTaskDto.getTaskDefinitionKey());
		}
	}

	private String getTaskAsString() {
		WebTarget filteredTaskTarget = getProcessTarget();
		String responseString = filteredTaskTarget //
				.request(MediaType.APPLICATION_JSON_TYPE) //
				.accept(MediaType.APPLICATION_JSON_TYPE) //
				.get(String.class);

		return responseString;
	}

	private List<MyTaskDto> getTaskList() {
		WebTarget filteredTaskTarget = getProcessTarget();
		List<MyTaskDto> myTaskDtoList = Arrays.asList(filteredTaskTarget //
				.request(MediaType.APPLICATION_JSON_TYPE) //
				.accept(MediaType.APPLICATION_JSON_TYPE) //
				.get(MyTaskDto[].class));

		return myTaskDtoList;
	}

	private WebTarget getProcessTarget() {
		WebTarget target = client.target(this.baseAddress);
		target = target.path("task");
		target = target.queryParam("taskDefinitionKey", "UserTask1");
		return target;
	}

	private WebTarget getTaskIdClaimTarget(String id) {
		WebTarget target = client.target(this.baseAddress);
		target = target.path("task");
		target = target.path(id);
		target = target.path("claim");
		return target;
	}

	private WebTarget getTaskIdCompleteTarget(String id) {
		WebTarget target = client.target(this.baseAddress);
		target = target.path("task");
		target = target.path(id);
		target = target.path("complete");
		return target;
	}
}
