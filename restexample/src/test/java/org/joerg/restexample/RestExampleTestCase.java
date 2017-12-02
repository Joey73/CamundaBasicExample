package org.joerg.restexample;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Ignore;

@Ignore
public class RestExampleTestCase extends ProcessEngineTestCase {

	@Deployment(resources = { "restexample.bpmn" })
	public void testHappyPath1() {
		// Start process
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");

		// Get first task
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		assertEquals("UserTask1", task.getTaskDefinitionKey());

		// Set variable
		VariableMap variables = Variables.createVariables();
		variables.clear();
		variables.put("FormFieldOption", "Test2");

		// Complete task
		taskService.complete(task.getId(), variables);

		assertProcessEnded(pi.getId());
	}

	@Deployment(resources = { "restexample.bpmn" })
	public void testHappyPath2() {
		// Start process
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");

		// Get first task
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
		assertEquals("UserTask1", task.getTaskDefinitionKey());

		// Set variable
		VariableMap variables = Variables.createVariables();
		variables.clear();
		variables.put("FormFieldOption", "Test3");

		// Complete task
		taskService.complete(task.getId(), variables);

		assertProcessEnded(pi.getId());
	}

}
