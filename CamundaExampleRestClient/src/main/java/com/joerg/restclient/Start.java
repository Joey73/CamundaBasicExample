package com.joerg.restclient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class Start {
	public static final String BASE_ADDRESS = "http://localhost:8080/engine-rest";

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();

		// ProcessDefinition
		ProcessDefinition processDefinition = new ProcessDefinition(BASE_ADDRESS, client);
		//processDefinition.printProcessDefinitionString();
		processDefinition.printProcessDefinition();

		// Start process instance
		processDefinition.startProcessInstance();

		// Active Task
		Task task = new Task(BASE_ADDRESS, client);
		task.printTaskList();

		// Claim tasks
		task.claimAllTasks();

		// Complete task (with parameter value)
		task.completeAllTasks();
		
		task.printTaskList();
	}
}
