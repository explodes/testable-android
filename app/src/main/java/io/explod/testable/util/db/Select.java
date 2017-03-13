package io.explod.testable.util.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Select extends Query<Select> {

	@Nullable
	private String[] mColumns;

	@Nullable
	private String mWhere;

	@Nullable
	private String[] mWhereArgs;

	@Nullable
	private String mGroupBy;

	@Nullable
	private String mHaving;

	@Nullable
	private String mOrderBy;

	@Nullable
	private String mLimit;

	Select(@NonNull SQLiteDatabase db) {
		super(db);
	}

	@NonNull
	public Select table(@NonNull String table) {
		super.table(table);
		return this;
	}

	@NonNull
	public Select columns(@NonNull String[] columns) {
		mColumns = columns;
		return this;
	}

	public Select byId(long id) {
		return where(BaseColumns._ID + " = ?", new String[]{String.valueOf(id)});
	}

	@NonNull
	public Select where(@NonNull String where) {
		return where(where, null);
	}

	@NonNull
	public Select where(@NonNull String where, @Nullable String[] whereArgs) {
		mWhere = where;
		mWhereArgs = whereArgs;
		return this;
	}

	@NonNull
	public Select group(@NonNull String groupBy) {
		mGroupBy = groupBy;
		return this;
	}

	@NonNull
	public Select having(@NonNull String having) {
		mHaving = having;
		return this;
	}

	@NonNull
	public Select sort(@NonNull String sort) {
		mOrderBy = sort;
		return this;
	}

	@NonNull
	public Select limit(@NonNull String limit) {
		mLimit = limit;
		return this;
	}

	@Override
	protected void validate() {
		super.validate();
		if (mColumns == null) {
			throw new NullPointerException("Selected columns cannot be null");
		}
	}

	@NonNull
	public Cursor execute() {
		validate();
		if (mLimit == null) {
			return getDatabase().query(getTable(), mColumns, mWhere, mWhereArgs, mGroupBy, mHaving, mOrderBy);
		} else {
			return getDatabase().query(getTable(), mColumns, mWhere, mWhereArgs, mGroupBy, mHaving, mOrderBy, mLimit);
		}
	}

}
