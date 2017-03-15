package io.explod.testable.module;

import javax.inject.Singleton;

import dagger.Component;
import io.explod.testable.module.modules.AppModule;
import io.explod.testable.module.modules.DatabaseModule;
import io.explod.testable.module.modules.GsonModule;
import io.explod.testable.module.modules.NetworkModule;
import io.explod.testable.module.modules.RepoModule;
import io.explod.testable.module.modules.ServiceModule;

@Component(
	modules = {
		AppModule.class,
		DatabaseModule.class,
		RepoModule.class,
		GsonModule.class,
		NetworkModule.class,
		ServiceModule.class,
	}
)
@Singleton
public interface ObjectComponent extends Injector {
}
