package io.explod.testable.util.db;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

public class Insert extends QueryWithValues<Insert> {

	Insert(@NonNull SQLiteDatabase db) {
		super(db);
	}

	@NonNull
	public Insert table(@NonNull String table) {
		super.table(table);
		return this;
	}

	public long execute() {
		validate();
		return getDatabase().insert(getTable(), null, getValues());
	}
}
