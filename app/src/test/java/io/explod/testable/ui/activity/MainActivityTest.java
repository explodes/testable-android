package io.explod.testable.ui.activity;

import android.app.Activity;

import org.junit.Test;
import org.robolectric.Robolectric;

import io.explod.testable.R;
import meta.BaseRoboTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest extends BaseRoboTest {

	@Test
	public void titleIsCorrect() throws Exception {
		Activity activity = Robolectric.setupActivity(MainActivity.class);

		waitForMainLooper();

		assertEquals("explodes", activity.getTitle().toString());
	}

	@Test
	public void hasFragmentContainer() {
		Activity activity = Robolectric.setupActivity(MainActivity.class);

		assertNotNull(activity.findViewById(R.id.container));
	}

}
