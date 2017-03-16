package meta;

import android.support.annotation.NonNull;

import io.explod.testable.App;
import io.explod.testable.module.modules.AppModule;
import meta.module.DaggerTestObjectComponent;
import meta.module.TestObjectComponent;

public class TestApp extends App {

	@NonNull
	@Override
	protected TestObjectComponent buildObjectComponent() {
		return DaggerTestObjectComponent.builder()
			.appModule(new AppModule(this))
			.build();
	}

}
