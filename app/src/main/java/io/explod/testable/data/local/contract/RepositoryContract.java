package io.explod.testable.data.local.contract;

import android.provider.BaseColumns;

public class RepositoryContract {

	public static final String TABLE = "repositories";

	public static final class Columns implements BaseColumns {
		public static final String USER_ID = "user_id";
		public static final String NAME = "name";
	}

	public static final class Projection {
		public static final String[] ALL = new String[]{Columns._ID, Columns.USER_ID, Columns.NAME};
	}

	public static final class Sort {
		public static final String DEFAULT = null;
	}
}
