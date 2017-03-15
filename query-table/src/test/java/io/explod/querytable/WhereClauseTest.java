package io.explod.querytable;

import android.content.ContentValues;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class WhereClauseTest {

	private static <T> void assertArrayEquals(T[] one, T[] two) {
		if (one == null && two == null) return;
		if (one == null) assertNotNull(one);
		if (two == null) assertNotNull(two);
		if (one.length != two.length) assertEquals(one.length, two.length);
		for (int index = 0, N = one.length; index < N; index++) {
			assertEquals(one[index], two[index]);
		}
	}

	@Test
	public void fromContentValues_single() throws Exception {
		ContentValues values = new ContentValues(1);
		values.put("column", "value");

		WhereClause where = WhereClause.fromContentValues(values);
		assertNotNull(where);
		assertEquals("column = ?", where.getStatement());
		assertArrayEquals(new String[]{"value"}, where.getArgs());
	}

	@Test
	public void fromContentValues_single_null() throws Exception {
		ContentValues values = new ContentValues(1);
		values.putNull("column");

		WhereClause where = WhereClause.fromContentValues(values);
		assertNotNull(where);
		assertEquals("column IS NULL", where.getStatement());
		assertArrayEquals(new String[]{}, where.getArgs());
	}

	@Test
	public void fromContentValues_multiple() throws Exception {
		// it is a coincidence that this test passes given that the order in which
		// ContentValues keys come out of its keySet().iterator() is not guaranteed.

		ContentValues values = new ContentValues(3);
		values.put("string", "abc");
		values.putNull("column");
		values.put("int", 123);
		WhereClause where = WhereClause.fromContentValues(values);

		assertNotNull(where);
		assertEquals("string = ? AND column IS NULL AND int = ?", where.getStatement());
		assertArrayEquals(new String[]{"abc", "123"}, where.getArgs());
	}

	@Test
	public void fromContentValues_null_values() throws Exception {
		WhereClause where = WhereClause.fromContentValues(null);
		assertNull(where);
	}

	@Test
	public void testToString() throws Exception {
		WhereClause where = new WhereClause("abc", "1", "2", "3");
		String str = where.toString();

		assertEquals("WHERE abc :: [1, 2, 3]", str);
	}

}