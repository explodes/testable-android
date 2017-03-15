package io.explod.testable;

import android.app.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import meta.BaseRoboTest;
import meta.TestApp;
import meta.module.modules.TestInternetConnectivityService;

import static meta.TestApp.getTestInternetConnectivityService;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TestAppTest extends BaseRoboTest {

	@Test
	public void testAppIsUsed() throws Exception {
		Application app = RuntimeEnvironment.application;

		assertTrue(app instanceof TestApp);
	}

	@Test
	public void canToggleInternetConnectivity() throws Exception {
		TestInternetConnectivityService service = getTestInternetConnectivityService();

		service.setConnected(true);
		assertTrue(service.isConnected());

		service.setConnected(false);
		assertFalse(service.isConnected());
	}

}
