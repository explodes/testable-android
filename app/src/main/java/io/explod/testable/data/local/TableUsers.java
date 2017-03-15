package io.explod.testable.data.local;


import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import io.explod.querydb.db.QueryDb;
import io.explod.querydb.table.QueryTable;
import io.explod.querydb.table.WhereClause;
import io.explod.testable.data.local.contract.UserContract;
import io.explod.testable.data.local.model.User;
import io.explod.testable.util.db.AsyncQueryTable;
import io.reactivex.Single;

import static android.content.ContentValues.TAG;

public class TableUsers extends AsyncQueryTable<User> {

	public TableUsers(@NonNull QueryDb db) {
		super(new QueryTable<>(db, UserContract.TABLE, User::fromCursor, UserContract.Sort.DEFAULT, UserContract.Projection.ALL));
	}

	public Single<User> getOrCreate(@NonNull String username) {
		Log.d(TAG, "UserTable.getOrCreate() called with: username = [" + username + "]");
		WhereClause where = new WhereClause(UserContract.Columns.USERNAME + " = ?", username);

		ContentValues values = new ContentValues();
		values.put(UserContract.Columns.USERNAME, username);

		return getOrCreate(where, values);
	}

}
