package io.explod.querytable;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.explod.querydb.CursorUtils;
import io.explod.querydb.Delete;
import io.explod.querydb.QueryDbHelper;
import io.explod.querydb.Select;
import io.explod.querydb.Update;
import io.explod.querytable.exception.CreateFailedException;
import io.explod.querytable.exception.NoRowsFoundException;

/**
 * QueryTable is a wrapper for {@link QueryDbHelper} that provides the most common functionality for
 * single-table (or view) queries.
 * <p>
 * It will also map cursor rows into your domain-objects using a provided {@link RowConverter}
 *
 * @param <T> T is the type of object to convert rows into
 */
public class QueryTable<T> {

	public interface RowConverter<T> {

		/**
		 * Convert a row provided by a cursor into an object.
		 * <p>
		 * This method must not close or operator on the cursor in any way except extracting column
		 * values.
		 *
		 * @param cursor Cursor to extract data from
		 * @return Converted object
		 */
		@NonNull
		T convertCurrentRow(@NonNull Cursor cursor);
	}

	private static final long NO_ID = -1;

	private static final String EXISTS_COLUMN = "has_rows";

	private static final String[] EXISTS_PROJECTION = new String[]{"COUNT(*) AS " + EXISTS_COLUMN};

	@NonNull
	private final QueryDbHelper mQueryDbHelper;

	@NonNull
	private final String mTableName;

	@NonNull
	private final RowConverter<T> mRowConverter;

	@NonNull
	private final String mDefaultSort;

	@NonNull
	private final String[] mProjection;

	/**
	 * Constructs a new QueryTable wrapper
	 *
	 * @param queryDbHelper QueryDbHelper to wrap
	 * @param tableName     Table (or view) to operate on
	 * @param rowConverter  Domain-object converter
	 * @param defaultSort   The default ORDER BY clause on select queries
	 * @param projection    The list of columns required to fully convert a row into your domain-object
	 */
	public QueryTable(@NonNull QueryDbHelper queryDbHelper, @NonNull String tableName, @NonNull RowConverter<T> rowConverter, @NonNull String defaultSort, @NonNull String[] projection) {
		mQueryDbHelper = queryDbHelper;
		mTableName = tableName;
		mRowConverter = rowConverter;
		mDefaultSort = defaultSort;
		mProjection = projection;
	}

	/**
	 * Fetch all rows using the default sort
	 *
	 * @return A list of objects found
	 */
	@NonNull
	public List<T> getAll() {
		return getAll(null);
	}

	/**
	 * Fetch rows that match the given {@link WhereClause} using the default sort
	 *
	 * @param where {@link WhereClause} to filter results by
	 * @return A list of objects found
	 */
	@NonNull
	public List<T> getAll(@Nullable WhereClause where) {
		return getAll(where, mDefaultSort);
	}

	/**
	 * Fetch sorted rows that match the given {@link WhereClause}
	 *
	 * @param where {@link WhereClause} to filter results by
	 * @param sort  ORDER BY clause
	 * @return A list of objects found
	 */
	@NonNull
	public List<T> getAll(@Nullable WhereClause where, @Nullable String sort) {
		return getAll(mQueryDbHelper.getReadableDatabase(), where, sort);
	}

	/**
	 * Fetch sorted rows that match the given {@link WhereClause}
	 *
	 * @param db    Database to use, maybe under transaction
	 * @param where {@link WhereClause} to filter results by
	 * @param sort  ORDER BY clause
	 * @return A list of objects found
	 */
	@NonNull
	public List<T> getAll(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @Nullable String sort) {
		List<T> results = new ArrayList<>();

		Cursor cursor = select(db, mProjection, where, sort);
		try {
			while (cursor.moveToNext()) {
				T t = mRowConverter.convertCurrentRow(cursor);
				results.add(t);
			}
		} finally {
			CursorUtils.close(cursor);
		}

		return results;
	}

	/**
	 * Create a SELECT cursor with the given parameters
	 *
	 * @param db         Database to use, maybe under transaction
	 * @param projection SELECT these columns
	 * @param where      WHERE these conditions are met
	 * @param sort       ORDER BY this clause
	 * @return Open Cursor for the given query
	 */
	@NonNull
	private Cursor select(@NonNull SQLiteDatabase db, @NonNull String[] projection, @Nullable WhereClause where, @Nullable String sort) {
		Select select = mQueryDbHelper.select(db)
			.table(mTableName)
			.columns(projection);

		if (where != null) {
			select.where(where.getStatement(), where.getArgs());
		}

		if (sort != null) {
			select.sort(sort);
		}

		return select.execute();
	}


	/**
	 * Fetch the first row in the table
	 *
	 * @return The first found record or null if no records exist
	 */
	@Nullable
	public T first() {
		return first(null);
	}

	/**
	 * Fetch the first row in the table meeting the given conditions using the default sort
	 *
	 * @param where {@link WhereClause} to filter the table by
	 * @return The first found record or null if no records exist
	 */
	@Nullable
	public T first(@Nullable WhereClause where) {
		return first(where, mDefaultSort);
	}

	/**
	 * Fetch the first row in the table meeting the given conditions using the given sort
	 *
	 * @param where {@link WhereClause} to filter the table by
	 * @param sort  ORDER BY clause to order the results for
	 * @return The first found record or null if no records exist
	 */
	@Nullable
	public T first(@Nullable WhereClause where, @Nullable String sort) {
		return first(mQueryDbHelper.getReadableDatabase(), where, sort);
	}

	/**
	 * Fetch the first row in the table meeting the given conditions using the given sort
	 *
	 * @param db    Database to use, maybe under transaction
	 * @param where {@link WhereClause} to filter the table by
	 * @param sort  ORDER BY clause to order the results for
	 * @return The first found record or null if no records exist
	 */
	@Nullable
	public T first(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @Nullable String sort) {
		T result = null;

		Cursor cursor = select(db, mProjection, where, sort);
		try {
			if (cursor.moveToNext()) {
				result = mRowConverter.convertCurrentRow(cursor);
			}
		} finally {
			CursorUtils.close(cursor);
		}

		return result;
	}

	/**
	 * Helper method to build a {@link WhereClause} to match by ID
	 *
	 * @param id ID to match on
	 * @return The built {@link WhereClause} that filters by ID
	 */
	@NonNull
	private static WhereClause whereParametersForId(long id) {
		return new WhereClause(BaseColumns._ID + " = ?", String.valueOf(id));
	}

	/**
	 * Fetch a record by id. The ID column is assumed to be {@link BaseColumns#_ID}
	 *
	 * @param id ID to match on
	 * @return The matching record, or null if it was not found
	 */
	@Nullable
	public T byId(long id) {
		return byId(mQueryDbHelper.getReadableDatabase(), id);
	}

	/**
	 * Fetch a record by id. The ID column is assumed to be {@link BaseColumns#_ID}
	 *
	 * @param db Database to use, maybe under transaction
	 * @param id ID to match on
	 * @return The matching record, or null if it was not found
	 */
	@Nullable
	public T byId(@NonNull SQLiteDatabase db, long id) {
		WhereClause values = whereParametersForId(id);
		return first(db, values, null);
	}

	/**
	 * Check to see if rows exist matching the given {@link WhereClause}
	 *
	 * @param where {@link WhereClause} to filter by
	 * @return Whether or not any rows were found
	 */
	public boolean exists(@Nullable WhereClause where) {
		return exists(mQueryDbHelper.getReadableDatabase(), where);
	}

	/**
	 * Check to see if rows exist matching the given {@link WhereClause}
	 *
	 * @param db    Database to use, maybe under transaction
	 * @param where {@link WhereClause} to filter by
	 * @return Whether or not any rows were found
	 */
	public boolean exists(@NonNull SQLiteDatabase db, @Nullable WhereClause where) {
		return count(db, where) > 0;
	}

	/**
	 * Count the number of records in the table
	 *
	 * @return The count of rows in the table or 0 if no rows were found
	 */
	public long count() {
		return count(null);
	}

	/**
	 * Count the number of matching records in the table
	 *
	 * @param where {@link WhereClause} to filter by
	 * @return The count of rows matching the {@link WhereClause} or 0 if no rows were found
	 */
	public long count(@Nullable WhereClause where) {
		return count(mQueryDbHelper.getReadableDatabase(), where);
	}

	/**
	 * Count the number of matching records in the table
	 *
	 * @param db    Database to use, maybe under transaction
	 * @param where {@link WhereClause} to filter by
	 * @return The count of rows matching the {@link WhereClause} or 0 if no rows were found
	 */
	public long count(@NonNull SQLiteDatabase db, @Nullable WhereClause where) {
		long count = 0L;

		Cursor cursor = select(db, EXISTS_PROJECTION, where, null);
		try {
			if (cursor.moveToNext()) {
				count = CursorUtils.getLong(cursor, EXISTS_COLUMN);
			}
		} finally {
			CursorUtils.close(cursor);
		}

		return count;
	}

	/**
	 * Insert a new row into the database
	 *
	 * @param values Values to put into the row
	 * @return The ID of the new record, or -1 if the insert failed
	 */
	public long insert(@NonNull ContentValues values) {
		return insert(mQueryDbHelper.getWritableDatabase(), values);
	}

	/**
	 * Insert a new row into the database
	 *
	 * @param db     Database to use, maybe under transaction
	 * @param values Values to put into the row
	 * @return The ID of the new record, or -1 if the insert failed
	 */
	public long insert(@NonNull SQLiteDatabase db, @NonNull ContentValues values) {
		return mQueryDbHelper.insert(db)
			.table(mTableName)
			.values(values)
			.execute();
	}

	/**
	 * Update rows matching the given filter with the given values
	 *
	 * @param where  {@link WhereClause} selecting rows to update
	 * @param values Values to update the rows with
	 * @return The number of updated rows
	 */
	public long update(@Nullable WhereClause where, @NonNull ContentValues values) {
		return update(mQueryDbHelper.getWritableDatabase(), where, values);
	}

	/**
	 * Update rows matching the given filter with the given values
	 *
	 * @param db     Database to use, maybe under transaction
	 * @param where  {@link WhereClause} selecting rows to update
	 * @param values Values to update the rows with
	 * @return The number of updated rows
	 */
	public long update(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @NonNull ContentValues values) {
		Update update = mQueryDbHelper.update(db)
			.table(mTableName)
			.values(values);

		if (where != null) {
			update.where(where.getStatement(), where.getArgs());
		} else {
			// empty selection doesn't return count in some versions of sqlite
			update.where("1");
		}

		update.values(values);

		return update.execute();
	}

	/**
	 * Delete all rows from the table
	 *
	 * @return Number of rows deleted
	 */
	public long delete() {
		return delete(null);
	}

	/**
	 * Delete filtered rows from the table
	 *
	 * @param where Delete rows matching the filter
	 * @return Number of rows deleted
	 */
	public long delete(@Nullable WhereClause where) {
		return delete(mQueryDbHelper.getWritableDatabase(), where);
	}

	/**
	 * Delete filtered rows from the table
	 *
	 * @param db    Database to use, maybe under transaction
	 * @param where Delete rows matching the filter
	 * @return Number of rows deleted
	 */
	public long delete(@NonNull SQLiteDatabase db, @Nullable WhereClause where) {
		Delete delete = mQueryDbHelper.delete(db)
			.table(mTableName);

		if (where != null) {
			delete.where(where.getStatement(), where.getArgs());
		} else {
			// empty selection doesn't return count in some versions of sqlite
			delete.where("1");
		}

		return delete.execute();
	}

	/**
	 * Update or insert a row
	 *
	 * @param where  Update rows matching this filter
	 * @param values Values to update the row with, or values to insert a new row
	 * @return Number of rows updated or inserted
	 */
	public long upsert(@Nullable WhereClause where, @NonNull ContentValues values) {
		return upsert(mQueryDbHelper.getWritableDatabase(), where, values);
	}

	/**
	 * Update or insert a row
	 *
	 * @param db     Database to use, maybe under transaction
	 * @param where  Update rows matching this filter
	 * @param values Values to update the row with, or values to insert a new row
	 * @return Number of rows updated or inserted
	 */
	public long upsert(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @NonNull ContentValues values) {
		db.beginTransaction();
		try {
			long count = update(db, where, values);
			if (count > 0) {
				db.setTransactionSuccessful();
				return count;
			}
			long id = insert(db, values);
			if (id == NO_ID) {
				// if no id was returned, we didn't insert any values
				return 0;
			} else {
				db.setTransactionSuccessful();
				return 1;
			}
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * Get or create a row. In order to return a {@link NonNull} value, some exceptions may be thrown
	 * in the event of failure.
	 *
	 * @param where  Update rows matching this filter
	 * @param values Values to insert a new row if one was not found
	 * @return Found object or new object
	 * @throws CreateFailedException If the insert was not successful
	 * @throws NoRowsFoundException  If the newly inserted row could not be retrieved by its own ID
	 */
	@NonNull
	public T getOrCreate(@Nullable WhereClause where, @NonNull ContentValues values) {
		return getOrCreate(mQueryDbHelper.getWritableDatabase(), where, values);
	}

	/**
	 * Get or create a row. In order to return a {@link NonNull} value, some exceptions may be thrown
	 * in the event of failure.
	 *
	 * @param db     Database to use, maybe under transaction
	 * @param where  Update rows matching this filter
	 * @param values Values to insert a new row if one was not found
	 * @return Found object or new object
	 * @throws CreateFailedException If the insert was not successful
	 * @throws NoRowsFoundException  If the newly inserted row could not be retrieved by its own ID
	 */
	@NonNull
	public T getOrCreate(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @NonNull ContentValues values) {

		db.beginTransaction();
		try {
			T result = first(db, where, mDefaultSort);

			if (result != null) {
				db.setTransactionSuccessful();
				return result;
			}

			// create object
			long id = insert(db, values);
			if (id == NO_ID) {
				throw new CreateFailedException(mTableName, values);
			}

			// fetch whole object by id
			result = byId(db, id);
			if (result == null) {
				throw new NoRowsFoundException(mTableName, whereParametersForId(id));
			}
			db.setTransactionSuccessful();
			return result;
		} finally {
			db.endTransaction();
		}
	}

}
