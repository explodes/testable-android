package meta;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.explod.testable.service.base.InternetConnectivityService;
import meta.module.modules.TestInternetConnectivityService;

import static meta.module.TestObjectGraph.getTestInjector;

public class TestModules {

	@NonNull
	private static final TestModules INSTANCE = new TestModules();

	@Inject InternetConnectivityService testInternetConnectivityService;

	private TestModules() {
		getTestInjector().inject(this);
	}

	@NonNull
	public static TestInternetConnectivityService getTestInternetConnectivityService() {
		return (TestInternetConnectivityService) INSTANCE.testInternetConnectivityService;
	}

}
