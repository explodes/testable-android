package io.explod.testable;

import org.junit.Test;

import meta.BaseRoboTest;

import static org.junit.Assert.assertNotNull;

public class AppImplTest extends BaseRoboTest {

	@Test
	public void buildObjectComponent() throws Exception {
		AppImpl app = new AppImpl();

		assertNotNull(app.buildObjectComponent());
	}

}