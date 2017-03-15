package io.explod.testable.data.local.contract;

import android.provider.BaseColumns;
import android.support.annotation.Nullable;

public class UserContract {

	public static final String TABLE = "users";

	public static final class Columns implements BaseColumns {
		public static final String USERNAME = "username";
	}

	public static final class Projection {
		public static final String[] ALL = new String[]{Columns._ID, Columns.USERNAME};
	}

	public static final class Sort {
		@Nullable public static final String DEFAULT = null;
	}

}
