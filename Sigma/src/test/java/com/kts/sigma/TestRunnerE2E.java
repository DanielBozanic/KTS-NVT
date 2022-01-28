package com.kts.sigma;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunnerE2E {

	public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(TestSuiteE2E.class);

	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      
	      System.out.println(result.getRunCount());
	      System.out.println(result.wasSuccessful());
	 }
}
