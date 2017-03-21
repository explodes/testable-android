package io.explod.testable.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import io.explod.querydb.db.Migration;
import io.explod.querydb.db.QueryDb;
import io.explod.querydb.table.QueryTable;
import io.explod.querydb.util.CursorUtils;
import meta.BaseRoboTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

public class AsyncQueryTableTest extends BaseRoboTest {

	private static class Record {
		public long id;
		public String name;
		public int value;

		@NonNull
		private static Record fromCursor(@NonNull Cursor cursor) {
			Record record = new Record();
			record.id = CursorUtils.getId(cursor);
			record.name = CursorUtils.getString(cursor, "name");
			record.value = CursorUtils.getInt(cursor, "value");
			return record;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Record record = (Record) o;

			if (value != record.value) return false;
			return name != null ? name.equals(record.name) : record.name == null;

		}
	}

	private static class TestDatabase extends QueryDb {

		public TestDatabase() {
			super(RuntimeEnvironment.application, ":memory:", null, VERSION_INITIAL);
		}

		@Nullable
		@Override
		protected Migration getMigration(int version) {
			switch (version) {
				case VERSION_INITIAL:
					return db -> db.execSQL("CREATE TABLE test (_id INTEGER PRIMARY KEY, name TEXT NOT NULL, value INTEGER NOT NULL)");
			}
			return null;
		}
	}

	private static class RecordTable extends QueryTable<Record> {

		public RecordTable(@NonNull QueryDb db) {
			super(db, "test", Record::fromCursor, null, new String[]{"_id", "name", "value"});
		}
	}

	private static final Record[] EMPTY_RECORDS = new Record[]{};

	TestDatabase db;
	RecordTable table;
	AsyncQueryTable<Record> async;

	@Before
	public void setUp() throws Exception {
		db = new TestDatabase();
		table = new RecordTable(db);
		async = new AsyncQueryTable<>(table);
	}

	@After
	public void tearDown() throws Exception {
		db.close();
	}

	@Test
	public void getQueryTable() throws Exception {
		assertSame(table, async.getQueryTable());
	}

	@Test
	public void getAll() throws Exception {
		assertArrayEquals(EMPTY_RECORDS, async.getAll().blockingGet().toArray(EMPTY_RECORDS));
		assertArrayEquals(EMPTY_RECORDS, async.getAll("1=1").blockingGet().toArray(EMPTY_RECORDS));
		assertArrayEquals(EMPTY_RECORDS, async.getAll(db.getReadableDatabase(), "name DESC", "1=1").blockingGet().toArray(EMPTY_RECORDS));
	}

	@Test
	public void getAllSorted() throws Exception {
		assertArrayEquals(EMPTY_RECORDS, async.getAllSorted(null, "1=1").blockingGet().toArray(EMPTY_RECORDS));
	}

	@Test
	public void first() throws Exception {
		assertFalse(async.first().blockingGet().isPresent());
		assertFalse(async.first("1=1").blockingGet().isPresent());
	}

	@Test
	public void firstSorted() throws Exception {
		assertFalse(async.firstSorted(null, "1=1").blockingGet().isPresent());
		assertFalse(async.firstSorted(db.getWritableDatabase(), null, "1=1").blockingGet().isPresent());
	}

	@Test
	public void byId() throws Exception {
		assertFalse(async.byId(1000L).blockingGet().isPresent());
		assertFalse(async.byId(db.getWritableDatabase(), 1000L).blockingGet().isPresent());
	}

	@Test
	public void exists() throws Exception {
		assertFalse(async.exists("1=1").blockingGet());
		assertFalse(async.exists(db.getReadableDatabase(), "1=1").blockingGet());
	}

	@Test
	public void count() throws Exception {
		assertEquals(0L, async.count().blockingGet().longValue());
		assertEquals(0L, async.count("1=1").blockingGet().longValue());
		assertEquals(0L, async.count(db.getReadableDatabase(), "1=1").blockingGet().longValue());
	}

	@Test
	public void insert() throws Exception {
		ContentValues values = new ContentValues();
		values.put("name", "foo");
		values.put("value", 3);

		assertNotEquals(-1L, async.insert(values).blockingGet().longValue());
		assertNotEquals(-1L, async.insert(db.getWritableDatabase(), values).blockingGet().longValue());
	}

	@Test
	public void update() throws Exception {
		ContentValues values = new ContentValues();
		values.put("name", "foo");
		values.put("value", 3);

		assertEquals(0L, async.update(values, "1=1").blockingGet().longValue());
		assertEquals(0L, async.update(db.getWritableDatabase(), values, "1=1").blockingGet().longValue());
	}

	@Test
	public void delete() throws Exception {
		assertEquals(0L, async.delete().blockingGet().longValue());
		assertEquals(0L, async.delete("1=1").blockingGet().longValue());
		assertEquals(0L, async.delete(db.getWritableDatabase(), "1=1").blockingGet().longValue());
	}

	@Test
	public void upsert() throws Exception {
		ContentValues values = new ContentValues();
		values.put("name", "foo");
		values.put("value", 3);

		assertEquals(1L, async.upsert(values, "name=?", "foo").blockingGet().longValue());
		assertEquals(1L, async.upsert(db.getWritableDatabase(), values, "name=?", "foo").blockingGet().longValue());
	}

	@Test
	public void getOrCreate() throws Exception {
		ContentValues values = new ContentValues();
		values.put("name", "foo");
		values.put("value", 3);

		Record base = new Record();
		base.name = "foo";
		base.value = 3;

		assertEquals(base, async.getOrCreate(values, "name=?", "foo").blockingGet());
		assertEquals(base, async.getOrCreate(db.getWritableDatabase(), values, "name=?", "foo").blockingGet());
	}

}