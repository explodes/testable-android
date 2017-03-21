package io.explod.testable.data.local;


import android.content.ContentValues;
import android.support.annotation.NonNull;

import io.explod.querydb.db.QueryDb;
import io.explod.querydb.table.QueryTable;
import io.explod.testable.data.local.contract.UserContract;
import io.explod.testable.data.local.model.User;
import io.reactivex.Single;

public class UsersTable extends AsyncQueryTable<User> {

	public UsersTable(@NonNull QueryDb db) {
		super(new QueryTable<>(db, UserContract.TABLE, User::fromCursor, UserContract.Sort.DEFAULT, UserContract.Projection.ALL));
	}

	@NonNull
	public Single<User> getOrCreate(@NonNull String username) {
		ContentValues values = new ContentValues();
		values.put(UserContract.Columns.USERNAME, username);

		return getOrCreate(values, UserContract.Columns.USERNAME + " = ?", username);
	}

}
