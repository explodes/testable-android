package io.explod.testable.util.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Update extends QueryWithValues<Update> {

	@Nullable
	private String mWhere;

	@Nullable
	private String[] mWhereArgs;

	Update(@NonNull SQLiteDatabase db) {
		super(db);
	}

	@NonNull
	public Update table(@NonNull String table) {
		super.table(table);
		return this;
	}

	public Update byId(long id) {
		return where(BaseColumns._ID + " = ?", new String[]{String.valueOf(id)});
	}

	public Update where(@NonNull String whereClause) {
		return where(whereClause, null);
	}

	public Update where(@NonNull String whereClause, @Nullable String[] whereArgs) {
		mWhere = whereClause;
		mWhereArgs = whereArgs;
		return this;
	}

	public long execute() {
		validate();
		return getDatabase().update(getTable(), getValues(), mWhere, mWhereArgs);
	}

}
