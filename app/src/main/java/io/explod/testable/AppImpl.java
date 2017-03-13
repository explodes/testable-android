package io.explod.testable;

import android.support.annotation.NonNull;

import io.explod.testable.module.DaggerObjectComponent;
import io.explod.testable.module.ObjectComponent;
import io.explod.testable.module.modules.AppModule;

public class AppImpl extends App {

	@NonNull
	public ObjectComponent buildObjectComponent() {
		return DaggerObjectComponent.builder()
			.appModule(new AppModule(this))
			.build();
	}

}
