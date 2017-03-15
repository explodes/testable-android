package io.explod.testable.util.rx;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fernandocejas.arrow.optional.Optional;

public final class OptionalUtils {

	private OptionalUtils() {
	}

	@NonNull
	public static <T> Optional<T> optional(@Nullable T t) {
		if (t != null) {
			return Optional.of(t);
		} else {
			return Optional.absent();
		}
	}

}
