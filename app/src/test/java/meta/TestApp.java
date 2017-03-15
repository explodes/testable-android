package meta;

import android.support.annotation.NonNull;

import io.explod.testable.App;
import io.explod.testable.module.ObjectComponent;
import io.explod.testable.module.modules.AppModule;

public class TestApp extends App {

	@NonNull
	@Override
	protected ObjectComponent buildObjectComponent() {
		// todo: use DaggerTestObjectComponent
		return DaggerTestObjectComponent.builder()
			.appModule(new AppModule(this))
			.build();
	}

}
