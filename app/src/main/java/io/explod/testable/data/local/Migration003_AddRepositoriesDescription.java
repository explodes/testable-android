package io.explod.testable.data.local;


import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import io.explod.querydb.db.Migration;
import io.explod.testable.data.local.contract.RepositoryContract;

class Migration003_AddRepositoriesDescription implements Migration {
	@Override
	public void execute(@NonNull SQLiteDatabase db) {
		db.execSQL(String.format("ALTER TABLE %s ADD COLUMN %s TEXT NOT NULL DEFAULT ''",
			RepositoryContract.TABLE,
			RepositoryContract.Columns.DESCRIPTION
		));
	}
}
