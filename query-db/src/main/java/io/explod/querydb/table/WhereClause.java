package io.explod.querydb.table;


import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Provides table-level filtering for queries used in {@link QueryTable}
 */
public final class WhereClause {

	/**
	 * Build a WhereClause from ContentValues. Column-equality will be based on identity (=) or nullability (IS NULL)
	 *
	 * @param contentValues Values to build from
	 * @return If the provided ContentValues are null or empty, this function returns null.
	 */
	@Nullable
	public static WhereClause fromContentValues(@Nullable ContentValues contentValues) {
		if (contentValues == null || contentValues.size() == 0) {
			return null;
		}

		StringBuilder whereClause = new StringBuilder(32);
		List<String> whereArgs = new ArrayList<>(contentValues.size());

		Set<String> columns = contentValues.keySet();
		Iterator<String> columnIter = columns.iterator();

		while (columnIter.hasNext()) {
			String column = columnIter.next();

			Object columnValue = contentValues.get(column);
			if (columnValue == null) {
				whereClause.append(column);
				whereClause.append(" IS NULL");
			} else {
				whereClause.append(column);
				whereClause.append(" = ?");
				whereArgs.add(columnValue.toString());
			}

			if (columnIter.hasNext()) {
				whereClause.append(" AND ");
			}
		}

		return new WhereClause(whereClause.toString(), whereArgs.toArray(new String[0]));
	}

	@NonNull
	private final String mStatement;

	@NonNull
	private final String[] mArgs;

	/**
	 * Construct a new WhereClause
	 *
	 * @param statement Parametrized WHERE clause, such as {@code name LIKE ? AND occurrences > 10 AND weight > ?}
	 * @param args      Parameter values, such as "%rockwilder%", "1000"
	 */
	public WhereClause(@NonNull String statement, @NonNull String... args) {
		mStatement = statement;
		mArgs = args;
	}

	@NonNull
	public String getStatement() {
		return mStatement;
	}

	@NonNull
	public String[] getArgs() {
		return mArgs;
	}

	@Override
	public String toString() {
		return "WHERE " + mStatement + " :: " + Arrays.toString(mArgs);
	}
}
