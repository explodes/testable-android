package meta;

import android.support.annotation.NonNull;

import io.explod.testable.App;
import io.explod.testable.module.DaggerObjectComponent;
import io.explod.testable.module.ObjectComponent;
import io.explod.testable.module.modules.AppModule;

public class TestApp extends App {

	@NonNull
	@Override
	protected ObjectComponent buildObjectComponent() {
		// todo: use DaggerTestObjectComponent
		return DaggerObjectComponent.builder()
			.appModule(new AppModule(this))
			.build();
	}

}
