package io.explod.testable.data.local;


import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import io.explod.querydb.db.QueryDb;
import io.explod.querydb.table.QueryTable;
import io.explod.querydb.table.WhereClause;
import io.explod.testable.data.local.contract.RepositoryContract;
import io.explod.testable.data.local.model.Repository;
import io.explod.testable.util.db.AsyncQueryTable;
import io.reactivex.Single;

import static android.content.ContentValues.TAG;

public class TableRepositories extends AsyncQueryTable<Repository> {

	public TableRepositories(@NonNull QueryDb db) {
		super(new QueryTable<>(db, RepositoryContract.TABLE, Repository::fromCursor, RepositoryContract.Sort.DEFAULT, RepositoryContract.Projection.ALL));
	}

	public Single<Repository> getOrCreate(long userId, @NonNull String name) {
		Log.d(TAG, "RepositoryTable.getOrCreate() called with: userId = [" + userId + "], name = [" + name + "]");
		WhereClause where = new WhereClause(RepositoryContract.Columns.NAME + " = ?", name);

		ContentValues values = new ContentValues();
		values.put(RepositoryContract.Columns.USER_ID, userId);
		values.put(RepositoryContract.Columns.NAME, name);

		return getOrCreate(where, values);
	}
}
