package io.explod.testable.data.local.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import io.explod.querydb.util.CursorUtils;
import io.explod.testable.data.local.contract.UserContract;

public class User {

	private long id;

	private String name;

	@NonNull
	public static User fromCursor(@NonNull Cursor cursor) {
		User user = new User();
		user.id = CursorUtils.getId(cursor);
		user.name = CursorUtils.getString(cursor, UserContract.Columns.USERNAME);
		return user;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
