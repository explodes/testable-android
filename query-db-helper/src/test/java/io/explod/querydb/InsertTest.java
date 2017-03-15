package io.explod.querydb;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import meta.TestQueryDbHelper;

import static org.junit.Assert.assertNotEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class InsertTest {

	TestQueryDbHelper db;
	Insert query;

	@Before
	public void createQuery() {
		db = new TestQueryDbHelper();
		query = db.insert();
	}

	@After
	public void closeDatabase() {
		db.close();
	}

	@Test
	public void execute() throws Exception {
		long id = query.table("test").value("name", "foo").value("value", 123).execute();

		assertNotEquals(-1, id);
	}

}