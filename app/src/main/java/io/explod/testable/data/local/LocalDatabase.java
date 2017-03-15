package io.explod.testable.data.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.explod.querydb.db.Migration;
import io.explod.querydb.db.QueryDb;

public class LocalDatabase extends QueryDb {

	private static final String NAME = "app.db";

	private static final int VERSION = VERSION_INITIAL;

	@NonNull
	private final TableUsers mTableUsers;

	@NonNull
	private final TableRepositories mTableRepositories;

	public LocalDatabase(@NonNull Context context) {
		super(context, NAME, null, VERSION);
		mTableUsers = new TableUsers(this);
		mTableRepositories = new TableRepositories(this);
	}

	@NonNull
	public TableUsers users() {
		return mTableUsers;
	}

	@NonNull
	public TableRepositories repositories() {
		return mTableRepositories;
	}

	@Nullable
	@Override
	protected Migration getMigration(int version) {
		switch (version) {
			case VERSION_INITIAL:
				return (db) -> {
					new MigrationCreateUsersTable().execute(db);
					new MigrationCreateRepositoriesTable().execute(db);
				};
			default:
				return null;
		}
	}

}
