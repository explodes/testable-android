package io.explod.testable.util.mvp.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.PublishSubject;

public class BaseRxPresenter<V extends BaseView> extends MvpBasePresenter<V> {

	private static final Object GLOBAL_UNBIND = new Object();

	private final PublishSubject<Object> mDetachSubject = PublishSubject.create();

	@CallSuper
	@Override
	public void detachView(boolean retainInstance) {
		super.detachView(retainInstance);
		unbindAll();
	}

	/**
	 * Unsubscribe all observables, regardless of unbind key
	 */
	protected void unbindAll() {
		unbind(GLOBAL_UNBIND);
	}

	/**
	 * Unsubscribe all observables with a specific unbind key
	 */
	protected void unbind(@NonNull Object key) {
		mDetachSubject.onNext(key);
	}

	/**
	 * Subscribe until any detach key is sent.
	 */
	protected <T> Observable<T> bind(@NonNull Observable<T> source) {
		return bind(GLOBAL_UNBIND, source);
	}

	/**
	 * Subscribe until a specific unbind key is observed.
	 * <p>
	 * Subscription will end if either the unbindKey is null or the incoming key is null.
	 * Subscription will end if the unbind key equals the incoming unbind key.
	 */
	protected <T> Observable<T> bind(@NonNull final Object unbindKey, @NonNull Observable<T> source) {
		Observable<Object> until = mDetachSubject
			.filter(key -> GLOBAL_UNBIND.equals(key) || key.equals(unbindKey))
			.take(1);

		return source
			.takeUntil(until)
			.observeOn(AndroidSchedulers.mainThread());
	}

}
