package io.explod.testable.util;

import android.support.annotation.NonNull;

public final class TagUtils {

	private TagUtils() {
	}

	/**
	 * Create a TAG for a given class
	 */
	@NonNull
	public static String makeTag(@NonNull Class<?> klazz) {
		String tag = klazz.getSimpleName();
		return makeTag(tag);
	}

	/**
	 * Prepare a TAG from a given string.
	 */
	@NonNull
	public static String makeTag(@NonNull String tag) {
		return tag;
	}

}
