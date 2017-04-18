package io.explod.testable.ui.activity;

import android.app.Activity;

import org.junit.Rule;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowLooper;

import io.explod.testable.R;
import meta.BaseRoboTest;
import meta.rx.ImmediateSchedulerRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MainActivityTest extends BaseRoboTest {

	@Rule
	public ImmediateSchedulerRule mImmediateSchedulerRule = new ImmediateSchedulerRule();

	@Test
	public void titleIsCorrect() throws Exception {
		Activity activity = Robolectric.setupActivity(MainActivity.class);

		ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

		assertEquals("explodes", activity.getTitle().toString());
	}

	@Test
	public void hasFragmentContainer() {
		Activity activity = Robolectric.setupActivity(MainActivity.class);

		assertNotNull(activity.findViewById(R.id.container));
	}

}
