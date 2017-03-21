package io.explod.testable.data.local;


import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import io.explod.querydb.db.Migration;
import io.explod.testable.data.local.contract.RepositoryContract;

class Migration004_AddRepositoriesStarsWatchersForks implements Migration {
	@Override
	public void execute(@NonNull SQLiteDatabase db) {
		db.execSQL(String.format("ALTER TABLE %s ADD COLUMN %s INTEGER NOT NULL DEFAULT 0",
			RepositoryContract.TABLE,
			RepositoryContract.Columns.STARS
		));
		db.execSQL(String.format("ALTER TABLE %s ADD COLUMN %s INTEGER NOT NULL DEFAULT 0",
			RepositoryContract.TABLE,
			RepositoryContract.Columns.WATCHERS
		));
		db.execSQL(String.format("ALTER TABLE %s ADD COLUMN %s INTEGER NOT NULL DEFAULT 0",
			RepositoryContract.TABLE,
			RepositoryContract.Columns.FORKS
		));
	}
}
