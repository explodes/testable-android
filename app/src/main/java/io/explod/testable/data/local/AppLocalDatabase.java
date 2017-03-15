package io.explod.testable.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.explod.querydb.db.Migration;
import io.explod.querydb.db.QueryDb;
import io.explod.testable.data.local.contract.GithubRepositoryContract;
import io.explod.testable.data.local.contract.GithubUserContract;

public class AppLocalDatabase extends QueryDb {

	private static final String NAME = "app.db";

	private static final int VERSION = VERSION_INITIAL;

	public AppLocalDatabase(@NonNull Context context) {
		super(context, NAME, null, VERSION);
	}

	@Nullable
	@Override
	protected Migration getMigration(int version) {
		switch (version) {
			case VERSION_INITIAL:
				return (db) -> {
					new CreateGithubUserMigration().execute(db);
					new CreateGithubRepositoryMigration().execute(db);
				};
			default:
				return null;
		}
	}

	private class CreateGithubUserMigration implements Migration {

		@Override
		public void execute(@NonNull SQLiteDatabase db) {
			db.execSQL(String.format("CREATE TABLE %s (" +
					"%s INTEGER NOT NULL PRIMARY KEY, " +
					"%s TEXT NOT NULL" +
					")",
				GithubUserContract.TABLE,
				GithubUserContract.Columns._ID,
				GithubUserContract.Columns.NAME
			));
		}
	}

	private class CreateGithubRepositoryMigration implements Migration {
		@Override
		public void execute(@NonNull SQLiteDatabase db) {
			db.execSQL(String.format("CREATE TABLE %s (" +
					"%s INTEGER NOT NULL PRIMARY KEY, " +
					"%s INTEGER NOT NULL, " +
					"%s TEXT NOT NULL, " +
					"FOREIGN KEY (%s) REFERENCES %s(%s)" +
					")",
				GithubRepositoryContract.TABLE,
				GithubRepositoryContract.Columns._ID,
				GithubRepositoryContract.Columns.USER_ID,
				GithubRepositoryContract.Columns.NAME,
				GithubRepositoryContract.Columns.USER_ID, GithubUserContract.TABLE, GithubUserContract.Columns._ID
			));
		}
	}
}
