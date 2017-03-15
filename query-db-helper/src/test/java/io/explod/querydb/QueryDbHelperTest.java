package io.explod.querydb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import meta.TestQueryDbHelper;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class QueryDbHelperTest {

	TestQueryDbHelper db;

	@Before
	public void createDb() {
		db = new TestQueryDbHelper();
	}

	@After
	public void closeDb() {
		db.close();
	}

	@Test
	public void select() throws Exception {
		assertNotNull(db.select());
	}

	@Test
	public void insert() throws Exception {
		assertNotNull(db.insert());
	}

	@Test
	public void update() throws Exception {
		assertNotNull(db.update());
	}

	@Test
	public void delete() throws Exception {
		assertNotNull(db.delete());
	}

}