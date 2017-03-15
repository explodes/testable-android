package io.explod.testable.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ListUtils {

	@FunctionalInterface
	public interface Eq<A, B> {
		boolean call(@Nullable A a, @Nullable B b);
	}

	@FunctionalInterface
	public interface Search<A> {
		boolean call(@Nullable A a);
	}

	@FunctionalInterface
	public interface Transform<A, B> {
		@Nullable
		B call(@Nullable A a);
	}

	/**
	 * Check the existence of an object in a list based on a custom equality function instead of
	 * using the default .equals comparison
	 *
	 * @param haystack Haystack
	 * @param needle   Needle
	 * @param eq       Custom comparator
	 * @param <A>      Type of objects in the haystack
	 * @param <B>      Type of needle
	 * @return If the item was found in the haystack, that item was returned. null is returned otherwise
	 */
	@Nullable
	public static <A, B> A first(@Nullable Collection<A> haystack, @Nullable B needle, @NonNull Eq<A, B> eq) {
		if (haystack != null) {
			for (A item : haystack) {
				if (eq.call(item, needle)) {
					return item;
				}
			}
		}
		return null;
	}

	@NonNull
	public static <T> List<T> findAll(@NonNull Collection<T> items, @NonNull Search<T> search) {
		List<T> results = new ArrayList<>();
		for (T next : items) {
			if (search.call(next)) {
				results.add(next);
			}
		}
		return results;
	}

	/**
	 * Returns true if a list is null or empty.
	 */
	public static boolean isEmpty(@Nullable List<?> list) {
		return list == null || list.size() == 0;
	}


	public static <T> boolean any(@NonNull List<T> data, @NonNull Search<T> search) {
		for (T t : data) {
			if (search.call(t)) return true;
		}
		return false;
	}


	@NonNull
	public static <A, B> List<B> transform(@NonNull Collection<A> list, @NonNull Transform<A, B> transform) {
		List<B> results = new ArrayList<>(list.size());
		for (A a : list) {
			B b = transform.call(a);
			if (b != null) {
				results.add(transform.call(a));
			}
		}
		return results;
	}

	@NonNull
	public static <In extends Out, Out> List<Out> asList(@NonNull Collection<In> in) {
		return transform(in, (a) -> a);
	}

	@Nullable
	public static <T> ArrayList<T> toArrayList(@Nullable List<T> in) {
		if (in == null) return null;
		if (in instanceof ArrayList) return (ArrayList<T>) in;
		return new ArrayList<>(in);
	}

}
