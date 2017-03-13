package io.explod.testable.util.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Delete extends Query<Delete> {

	@Nullable
	private String mWhere;

	@Nullable
	private String[] mWhereArgs;

	Delete(@NonNull SQLiteDatabase db) {
		super(db);
	}

	@NonNull
	public Delete table(@NonNull String table) {
		super.table(table);
		return this;
	}

	public Delete byId(long id) {
		return where(BaseColumns._ID + " = ?", new String[]{String.valueOf(id)});
	}

	@NonNull
	public Delete where(@NonNull String where) {
		return where(where, null);
	}

	@NonNull
	public Delete where(@NonNull String where, @Nullable String[] whereArgs) {
		mWhere = where;
		mWhereArgs = whereArgs;
		return this;
	}

	public int execute() {
		validate();
		return getDatabase().delete(getTable(), mWhere, mWhereArgs);
	}

}
