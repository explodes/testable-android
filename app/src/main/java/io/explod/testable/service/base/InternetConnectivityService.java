package io.explod.testable.service.base;

import android.support.annotation.NonNull;

import io.reactivex.Observable;

public interface InternetConnectivityService {

	boolean isConnected();

	@NonNull
	Observable<Boolean> connectionObservable();

}
