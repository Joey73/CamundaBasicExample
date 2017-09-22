package org.joerg.restexample.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class Sysout10Delegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Sysout10Delegate is called with variables " + execution.getVariables());
	}
}
