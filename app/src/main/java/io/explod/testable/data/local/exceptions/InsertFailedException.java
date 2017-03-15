package io.explod.testable.data.local.exceptions;


import android.support.annotation.NonNull;

public class InsertFailedException extends DatabaseActionFailedException {
	public InsertFailedException(@NonNull String message) {
		super(message);
	}
}
