package io.explod.testable.util.db;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

public interface Migration {
	void execute(@NonNull SQLiteDatabase db);
}
