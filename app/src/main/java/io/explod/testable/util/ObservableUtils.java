package io.explod.testable.util;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.fernandocejas.arrow.optional.Optional;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static io.explod.querydb.util.CursorUtils.close;


public class ObservableUtils {

	@FunctionalInterface
	public interface ThrowableFunc<T> {
		@NonNull
		T call() throws Throwable;
	}

	@FunctionalInterface
	public interface Transformer<R, T> {
		@NonNull
		T call(@NonNull R arg);
	}

	@NonNull
	public static <T> Observable<T> asObservable(@NonNull ThrowableFunc<T> func) {
		return Observable.create(s -> {
			try {
				T result = func.call();
				s.onNext(result);
				s.onComplete();
			} catch (Throwable t) {
				s.onError(t);
			}
		});
	}

	@NonNull
	public static <T> Observable<T> asObservableIo(@NonNull ThrowableFunc<T> func) {
		return asObservable(func)
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public static <T> Observable<T> eachRow(@NonNull Cursor cursor, @NonNull Transformer<Cursor, T> transform) {
		return Observable.<T>create(
			s -> {
				try {
					while (cursor.moveToNext()) {
						T t = transform.call(cursor);
						s.onNext(t);
					}
					s.onComplete();
				} catch (Exception ex) {
					s.onError(ex);
				} finally {
					close(cursor);
				}
			})
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public static <T> Observable<Optional<T>> firstRow(@NonNull Cursor cursor, @NonNull Transformer<Cursor, T> transform) {
		return asObservableIo(() -> {
			try {
				if (cursor.moveToNext()) {
					T t = transform.call(cursor);
					return Optional.of(t);
				}
				return Optional.<T>absent();
			} finally {
				close(cursor);
			}
		});
	}

}
