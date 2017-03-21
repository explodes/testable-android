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
	public Single<Long> upsert(long userId, @NonNull String name, @NonNull String description, int forks, int stars, int watchers) {
		ContentValues values = new ContentValues();
		values.put(RepositoryContract.Columns.USER_ID, userId);
		values.put(RepositoryContract.Columns.NAME, name);
		values.put(RepositoryContract.Columns.DESCRIPTION, description);
		values.put(RepositoryContract.Columns.FORKS, forks);
		values.put(RepositoryContract.Columns.WATCHERS, watchers);
		values.put(RepositoryContract.Columns.STARS, stars);

		String where = String.format("%s = ? AND %s = ?", RepositoryContract.Columns.USER_ID, RepositoryContract.Columns.NAME);

		return upsert(values, where, String.valueOf(userId), name);
	}

	@NonNull
	public Single<List<Repository>> getAllForUser(long userId) {
		return getAllSorted(RepositoryContract.Sort.NAME, RepositoryContract.Columns.USER_ID + " = ?", String.valueOf(userId));
	}
}
