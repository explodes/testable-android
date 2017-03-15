package io.explod.testable.data.local;


import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import io.explod.querydb.db.Migration;
import io.explod.testable.data.local.contract.UserContract;

class MigrationCreateUsersTable implements Migration {
	@Override
	public void execute(@NonNull SQLiteDatabase db) {
		db.execSQL(String.format("CREATE TABLE %s (" +
				"%s INTEGER NOT NULL PRIMARY KEY, " +
				"%s TEXT NOT NULL" +
				")",
			UserContract.TABLE,
			UserContract.Columns._ID,
			UserContract.Columns.USERNAME
		));
	}
}
