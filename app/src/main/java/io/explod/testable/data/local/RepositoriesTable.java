package io.explod.testable.data.local;


import android.content.ContentValues;
import android.support.annotation.NonNull;

import java.util.List;

import io.explod.querydb.db.QueryDb;
import io.explod.querydb.table.QueryTable;
import io.explod.querydb.table.WhereClause;
import io.explod.testable.data.local.contract.RepositoryContract;
import io.explod.testable.data.local.model.Repository;
import io.reactivex.Single;

public class RepositoriesTable extends AsyncQueryTable<Repository> {

	public RepositoriesTable(@NonNull QueryDb db) {
		super(new QueryTable<>(db, RepositoryContract.TABLE, Repository::fromCursor, RepositoryContract.Sort.DEFAULT, RepositoryContract.Projection.ALL));
	}

	@NonNull
	public Single<Repository> getOrCreate(long userId, @NonNull String name) {
		WhereClause where = new WhereClause(RepositoryContract.Columns.NAME + " = ?", name);

		ContentValues values = new ContentValues();
		values.put(RepositoryContract.Columns.USER_ID, userId);
		values.put(RepositoryContract.Columns.NAME, name);

		return getOrCreate(where, values);
	}

	@NonNull
	public Single<List<Repository>> getAllForUser(long userId) {
		WhereClause where = new WhereClause(RepositoryContract.Columns.USER_ID + " = ?", String.valueOf(userId));

		return getAll(where);
	}
}
