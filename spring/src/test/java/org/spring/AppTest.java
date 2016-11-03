package org.spring;

import org.springframework.beans.BeanUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		LessClass l = new LessClass();
		l.setName("name");
		MoreClass m = new MoreClass();
		m.setAge(11);
		m.setId(111);
		m.setName("n");
		
		BeanUtils.copyProperties(m, l);
		System.out.println(l.getName());
		assertTrue(true);
	}
}
