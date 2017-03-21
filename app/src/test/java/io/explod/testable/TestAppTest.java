package io.explod.testable;

import android.app.Application;

import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import meta.BaseRoboTest;
import meta.TestApp;
import meta.TestModules;
import meta.service.TestInternetConnectivityService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAppTest extends BaseRoboTest {

	@Test
	public void testAppIsUsed() throws Exception {
		Application app = RuntimeEnvironment.application;

		assertTrue(app instanceof TestApp);
	}

	@Test
	public void canToggleInternetConnectivity() throws Exception {
		TestInternetConnectivityService service = new TestModules().testInternetConnectivityService;

		service.setConnected(true);
		assertTrue(service.isConnected());

		service.setConnected(false);
		assertFalse(service.isConnected());
	}

}
