package io.explod.testable.data.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.explod.querydb.db.Migration;
import io.explod.querydb.db.QueryDb;

public class AppDatabase extends QueryDb {

	private static final String NAME = "app.db";

	private static final int VERSION = VERSION_INITIAL;

	@NonNull
	private final UsersTable mUsersTable;

	@NonNull
	private final RepositoriesTable mRepositoriesTable;

	public AppDatabase(@NonNull Context context) {
		super(context, NAME, null, VERSION);
		mUsersTable = new UsersTable(this);
		mRepositoriesTable = new RepositoriesTable(this);
	}

	@NonNull
	public UsersTable users() {
		return mUsersTable;
	}

	@NonNull
	public RepositoriesTable repositories() {
		return mRepositoriesTable;
	}

	@Nullable
	@Override
	protected Migration getMigration(int version) {
		switch (version) {
			case VERSION_INITIAL:
				return (db) -> {
					new Migration001_CreateUsersTable().execute(db);
					new Migration002_CreateRepositoriesTable().execute(db);
				};
			default:
				return null;
		}
	}

}
