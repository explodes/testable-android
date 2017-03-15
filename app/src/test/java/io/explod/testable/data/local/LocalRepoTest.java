package io.explod.testable.data.local;

import android.annotation.SuppressLint;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import meta.BaseRoboTest;
import meta.rx.ImmediateSchedulerRule;

@SuppressLint("DefaultLocale")
@RunWith(RobolectricTestRunner.class)
public class LocalRepoTest extends BaseRoboTest {

	@Rule
	public ImmediateSchedulerRule rule = new ImmediateSchedulerRule();

	LocalDatabase db;

	@Before
	public void createRepo() {
		db = new LocalDatabase(RuntimeEnvironment.application);
	}

	@After
	public void closeDatabase() {
		db.close();
	}

}