package meta;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.explod.testable.App;
import io.explod.testable.module.modules.AppModule;
import io.explod.testable.service.base.InternetConnectivityService;
import meta.module.DaggerTestObjectComponent;
import meta.module.TestObjectComponent;
import meta.module.modules.TestInternetConnectivityService;

import static meta.module.TestObjectGraph.getTestInjector;

public class TestApp extends App {

	@NonNull
	@Override
	protected TestObjectComponent buildObjectComponent() {
		return DaggerTestObjectComponent.builder()
			.appModule(new AppModule(this))
			.build();
	}

}
