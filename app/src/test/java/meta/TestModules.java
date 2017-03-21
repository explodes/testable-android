package meta;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.explod.testable.service.base.InternetConnectivityService;
import meta.service.TestInternetConnectivityService;

import static meta.module.TestObjectGraph.getTestInjector;

public class TestModules {

	@Inject
	InternetConnectivityService internetConnectivityService;

	@NonNull
	public final TestInternetConnectivityService testInternetConnectivityService;

	public TestModules() {
		getTestInjector().inject(this);
		testInternetConnectivityService = (TestInternetConnectivityService) internetConnectivityService;
	}

}
