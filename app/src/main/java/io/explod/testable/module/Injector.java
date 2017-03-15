package io.explod.testable.module;

import io.explod.testable.data.AppRepo;
import io.explod.testable.data.OfflineFirstObservable;
import io.explod.testable.ui.activity.MainActivity;

public interface Injector {
	void inject(MainActivity target);

	void inject(AppRepo target);

	void inject(OfflineFirstObservable.InternetConnectivityServiceInjector target);
}