package io.explod.testable.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import io.explod.testable.service.base.InternetConnectivityService;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

import static io.explod.testable.App.getApp;


public class InternetConnectivityServiceImpl implements InternetConnectivityService {

	@NonNull
	private final BehaviorSubject<Boolean> mConnectionStatusSubject = BehaviorSubject.create();

	@NonNull
	private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(@NonNull Context context, @NonNull Intent intent) {
			updateStatus(context);
		}
	};

	public InternetConnectivityServiceImpl() {
		Context context = getApp();
		updateStatus(context);
		receiveConnectionEvents(context);
	}

	private void updateStatus(@NonNull Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

		boolean connected = activeNetwork != null && activeNetwork.isConnected();
		mConnectionStatusSubject.onNext(connected);
	}

	private void receiveConnectionEvents(@NonNull Context context) {
		context.registerReceiver(mConnectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}

	@Override
	public boolean isConnected() {
		return mConnectionStatusSubject.getValue();
	}

	@NonNull
	@Override
	public Observable<Boolean> connectionObservable() {
		return mConnectionStatusSubject.distinctUntilChanged();
	}
}
