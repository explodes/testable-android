package io.explod.testable.ui.activity;

import android.app.Activity;

import org.junit.Test;
import org.robolectric.Robolectric;

import meta.BaseRoboTest;

import static org.junit.Assert.assertEquals;

public class MainActivityTest extends BaseRoboTest {

	@Test
	public void titleIsCorrect() {
		Activity activity = Robolectric.setupActivity(MainActivity.class);

		assertEquals("Testable", activity.getTitle().toString());
	}

}
