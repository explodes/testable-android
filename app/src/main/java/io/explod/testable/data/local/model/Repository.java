package io.explod.testable.data.local.model;

import android.database.Cursor;
import android.support.annotation.NonNull;

import io.explod.querydb.util.CursorUtils;
import io.explod.testable.data.local.contract.RepositoryContract;

public class Repository {

	private long id;

	private long userId;

	private String name;

	private String description;

	private int forks;

	private int watchers;

	private int stars;

	@NonNull
	public static Repository fromCursor(@NonNull Cursor cursor) {
		Repository repo = new Repository();
		repo.id = CursorUtils.getId(cursor);
		repo.userId = CursorUtils.getLong(cursor, RepositoryContract.Columns.USER_ID);
		repo.name = CursorUtils.getString(cursor, RepositoryContract.Columns.NAME);
		repo.description = CursorUtils.getString(cursor, RepositoryContract.Columns.DESCRIPTION);
		repo.forks = CursorUtils.getInt(cursor, RepositoryContract.Columns.FORKS);
		repo.watchers = CursorUtils.getInt(cursor, RepositoryContract.Columns.WATCHERS);
		repo.stars = CursorUtils.getInt(cursor, RepositoryContract.Columns.STARS);
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

	public String getDescription() {
		return description;
	}

	public int getForks() {
		return forks;
	}

	public int getWatchers() {
		return watchers;
	}

	public int getStars() {
		return stars;
	}
}
