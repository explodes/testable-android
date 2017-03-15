package io.explod.testable.data.local;


import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import io.explod.querydb.db.Migration;
import io.explod.testable.data.local.contract.RepositoryContract;
import io.explod.testable.data.local.contract.UserContract;

class MigrationCreateRepositoriesTable implements Migration {
	@Override
	public void execute(@NonNull SQLiteDatabase db) {
		db.execSQL(String.format("CREATE TABLE %s (" +
				"%s INTEGER NOT NULL PRIMARY KEY, " +
				"%s INTEGER NOT NULL, " +
				"%s TEXT NOT NULL, " +
				"FOREIGN KEY (%s) REFERENCES %s(%s)" +
				")",
			RepositoryContract.TABLE,
			RepositoryContract.Columns._ID,
			RepositoryContract.Columns.USER_ID,
			RepositoryContract.Columns.NAME,
			RepositoryContract.Columns.USER_ID, UserContract.TABLE, UserContract.Columns._ID
		));
	}
}
