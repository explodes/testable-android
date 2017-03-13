package io.explod.testable.data.local.contract;

import android.provider.BaseColumns;

public class GithubRepositoryContract {

	public static final String TABLE = "github_repository";

	public static final class Columns implements BaseColumns {
		public static final String USER_ID = "user_id";
		public static final String NAME = "name";
	}

	public static final class Projection {
		public static final String[] ALL = new String[]{Columns._ID, Columns.USER_ID, Columns.NAME};
	}
}
