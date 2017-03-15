package io.explod.testable.data;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.explod.testable.service.base.InternetConnectivityService;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static io.explod.testable.module.ObjectGraph.getInjector;

public class OfflineFirstObservable<T> {

	@NonNull
	public static <T> OfflineFirstObservable<T> from(@NonNull Single<T> offlineSource, @NonNull Single<T> onlineSource) {
		InternetConnectivityService internetConnectivityService = InternetConnectivityServiceInjector.get();
		return new OfflineFirstObservable<>(internetConnectivityService, offlineSource, onlineSource);
	}

	@NonNull
	private final InternetConnectivityService mInternetConnectivityService;

	@NonNull
	private final Single<T> mOnlineSource;

	@NonNull
	private final Single<T> mOfflineSource;

	public OfflineFirstObservable(@NonNull InternetConnectivityService internetConnectivityService, @NonNull Single<T> offlineSource, @NonNull Single<T> onlineSource) {
		mInternetConnectivityService = internetConnectivityService;
		mOfflineSource = offlineSource;
		mOnlineSource = onlineSource;
	}

	/**
	 * Returns local results followed by online results
	 */
	@NonNull
	public Observable<T> fromOfflineThenOnline() {
		return mInternetConnectivityService.connectionObservable()
			.subscribeOn(Schedulers.io())
			.take(1)
			.singleOrError()
			.flatMapObservable(connected -> {
				if (connected) {
					return Observable.concat(mOfflineSource.toObservable(), mOnlineSource.toObservable());
				} else {
					return mOfflineSource.toObservable();
				}
			});
	}

	/**
	 * Returns only offline results
	 */
	@NonNull
	public Single<T> fromOfflineOnly() {
		return mOfflineSource;
	}

	/**
	 * Returns results from online, if internet is
	 * available, otherwise it will return only offline results
	 */
	@NonNull
	public Single<T> fromOnlineOrOffline() {
		return mInternetConnectivityService.connectionObservable()
			.subscribeOn(Schedulers.io())
			.take(1)
			.singleOrError()
			.flatMap(connected -> connected ? mOnlineSource : mOfflineSource);
	}

	/**
	 * Workaround class to "inject" InternetConnectivityService into the typed parent class as
	 * Dagger doesn't allow injecting into typed or raw instances.
	 *
	 * @hide Visible only for injection
	 */
	public static class InternetConnectivityServiceInjector {

		@Inject
		InternetConnectivityService internetConnectivityService;

		public InternetConnectivityServiceInjector() {
			getInjector().inject(this);
		}

		@NonNull
		public static InternetConnectivityService get() {
			InternetConnectivityService service = new InternetConnectivityServiceInjector().internetConnectivityService;
			if (service == null) throw new NullPointerException("Unable to fetch service");
			return service;
		}

	}


}
