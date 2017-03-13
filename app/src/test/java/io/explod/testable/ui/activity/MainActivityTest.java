package io.explod.testable.ui.activity;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import meta.BaseRoboTest;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest extends BaseRoboTest {

	@Test
	public void titleIsCorrect() {
		Activity activity = Robolectric.setupActivity(MainActivity.class);

		assertEquals("Testable", activity.getTitle().toString());
	}

}
