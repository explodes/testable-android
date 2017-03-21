package io.explod.testable.data.local;


import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.util.List;

import io.explod.querydb.db.QueryDb;
import io.explod.querydb.table.QueryTable;
import io.explod.testable.data.local.contract.RepositoryContract;
import io.explod.testable.data.local.model.Repository;
import io.reactivex.Single;

public class RepositoriesTable extends AsyncQueryTable<Repository> {

	public RepositoriesTable(@NonNull QueryDb db) {
		super(new QueryTable<>(db, RepositoryContract.TABLE, Repository::fromCursor, RepositoryContract.Sort.DEFAULT, RepositoryContract.Projection.ALL));
	}

	@NonNull
	public Single<Repository> getOrCreate(long userId, @NonNull String name) {
		ContentValues values = new ContentValues();
		values.put(RepositoryContract.Columns.USER_ID, userId);
		values.put(RepositoryContract.Columns.NAME, name);

		return getOrCreate(values, RepositoryContract.Columns.NAME + " = ?", name);
	}

	@NonNull
	public Single<List<Repository>> getAllForUser(long userId) {
		return getAll(RepositoryContract.Columns.USER_ID + " = ?", String.valueOf(userId));
	}
}
