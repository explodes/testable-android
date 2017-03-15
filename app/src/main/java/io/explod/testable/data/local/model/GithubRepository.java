package io.explod.testable.data.local.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import io.explod.querydb.CursorUtils;
import io.explod.testable.data.local.contract.GithubRepositoryContract;

public class GithubRepository {

	private long id;

	private long userId;

	private String name;

	@NonNull
	public static GithubRepository fromCursor(@NonNull Cursor cursor) {
		GithubRepository repo = new GithubRepository();
		repo.id = CursorUtils.getId(cursor);
		repo.userId = CursorUtils.getLong(cursor, GithubRepositoryContract.Columns.USER_ID);
		repo.name = CursorUtils.getString(cursor, GithubRepositoryContract.Columns.NAME);
		return repo;
	}

	public long getId() {
		return id;
	}

	public long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}
}
