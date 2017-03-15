package io.explod.querytable.exception;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.explod.querytable.WhereClause;

public class NoRowsFoundException extends RuntimeException {
	public NoRowsFoundException(@NonNull String table, @Nullable WhereClause key) {
		super("No rows found on table table " + table + " for key " + key);
	}
}
