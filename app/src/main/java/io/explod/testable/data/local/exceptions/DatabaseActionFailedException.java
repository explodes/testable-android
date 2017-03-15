package io.explod.testable.data.local.exceptions;


import android.support.annotation.NonNull;

public class DatabaseActionFailedException extends Exception {
	public DatabaseActionFailedException(@NonNull String message) {
		super(message);
	}
}
