package io.explod.testable;

import android.app.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import meta.BaseRoboTest;
import meta.TestApp;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class TestAppTest extends BaseRoboTest {

	@Test
	public void testAppIsUsed() {
		Application app = RuntimeEnvironment.application;

		assertTrue(app instanceof TestApp);
	}
}
