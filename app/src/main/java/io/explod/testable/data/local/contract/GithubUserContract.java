package io.explod.testable.data.local.contract;

import android.provider.BaseColumns;

public class GithubUserContract {

	public static final String TABLE = "github_user";

	public static final class Columns implements BaseColumns {
		public static final String NAME = "name";
	}

	public static final class Projection {
		public static final String[] ALL = new String[]{Columns._ID, Columns.NAME};
	}

}
