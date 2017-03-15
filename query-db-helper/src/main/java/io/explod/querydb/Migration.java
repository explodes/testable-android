package io.explod.querydb;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

public interface Migration {
	void execute(@NonNull SQLiteDatabase db);
}
