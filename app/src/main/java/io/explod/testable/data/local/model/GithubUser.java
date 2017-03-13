package io.explod.testable.data.local.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import io.explod.testable.data.local.contract.GithubUserContract;
import io.explod.testable.util.db.CursorUtils;

public class GithubUser {

	private long id;

	private String name;

	@NonNull
	public static GithubUser fromCursor(@NonNull Cursor cursor) {
		GithubUser user = new GithubUser();
		user.id = CursorUtils.getId(cursor);
		user.name = CursorUtils.getString(cursor, GithubUserContract.Columns.NAME);
		return user;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
