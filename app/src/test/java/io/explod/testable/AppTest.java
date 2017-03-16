package io.explod.testable;

import org.junit.Test;

import meta.BaseRoboTest;

import static org.junit.Assert.assertNotNull;

public class AppTest extends BaseRoboTest {
	@Test
	public void getApp() throws Exception {
		assertNotNull(App.getApp());
	}

}