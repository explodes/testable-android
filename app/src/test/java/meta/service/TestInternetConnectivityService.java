package meta.service;

import android.support.annotation.NonNull;

import io.explod.testable.service.base.InternetConnectivityService;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class TestInternetConnectivityService implements InternetConnectivityService {

	@NonNull
	private final BehaviorSubject<Boolean> mConnectivitySubject = BehaviorSubject.create();

	public TestInternetConnectivityService() {
		setConnected(true);
	}

	public void setConnected(boolean connected) {
		mConnectivitySubject.onNext(connected);
	}

	@Override
	public boolean isConnected() {
		return mConnectivitySubject.getValue();
	}

	@NonNull
	@Override
	public Observable<Boolean> connectionObservable() {
		return mConnectivitySubject.distinctUntilChanged();
	}
}
