package io.explod.testable.data.local.exceptions;


import android.support.annotation.NonNull;

public class SelectFailedException extends DatabaseActionFailedException {
	public SelectFailedException(@NonNull String message) {
		super(message);
	}
}
