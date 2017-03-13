package io.explod.testable.util.db;

import android.support.annotation.NonNull;

public final class QueryUtils {

	public static final long NO_ID = -1;

	private QueryUtils() {/* no instance */}

	/**
	 * Given a table name, generate sql for aliases from input columns to output columns.
	 * <p>
	 * example:
	 * <p>
	 * {@code columnsAs("users", "id", "user_id", "name", "user_name")}
	 * <p>
	 * would result in:
	 * <p>
	 * {@code "users.id AS user_id, users.name AS user_name"}
	 *
	 * @param fromTable       Source table
	 * @param columnToColumns list of pairs of (source, destination) columns.
	 */
	@NonNull
	public static String columnsAs(@NonNull String fromTable, @NonNull String... columnToColumns) {
		if (columnToColumns.length % 2 != 0) {
			throw new IllegalArgumentException("Columns must be in pairs");
		}
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < columnToColumns.length; index += 2) {
			String fromColumn = columnToColumns[index];
			String toColumn = columnToColumns[index + 1];
			sb.append(fromTable);
			sb.append('.');
			sb.append(fromColumn);
			sb.append(" AS ");
			sb.append(toColumn);
			if (index + 2 < columnToColumns.length) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

}
