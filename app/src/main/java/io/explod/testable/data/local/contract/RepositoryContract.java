package io.explod.testable.data.local.contract;

import android.provider.BaseColumns;

public class RepositoryContract {

	public static final String TABLE = "repositories";

	public static final class Columns implements BaseColumns {
		public static final String USER_ID = "user_id";
		public static final String NAME = "name";
		public static final String DESCRIPTION = "description";
		public static final String STARS = "stars";
		public static final String WATCHERS = "watchers";
		public static final String FORKS = "forks";
	}

	public static final class Projection {
		public static final String[] ALL = new String[]{Columns._ID, Columns.USER_ID, Columns.NAME, Columns.DESCRIPTION, Columns.STARS, Columns.WATCHERS, Columns.FORKS};
	}

	public static final class Sort {
		public static final String NAME = String.format("lower(%s) ASC", Columns.NAME);
		public static final String DEFAULT = NAME;
	}
}
