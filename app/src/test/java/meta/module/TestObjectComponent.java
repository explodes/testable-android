package meta.module;


import javax.inject.Singleton;

import dagger.Component;
import io.explod.testable.module.ObjectComponent;
import io.explod.testable.module.modules.AppModule;
import io.explod.testable.module.modules.DatabaseModule;
import io.explod.testable.module.modules.RepoModule;
import meta.module.modules.TestServiceModule;

@Singleton
@Component(
	modules = {
		AppModule.class,
		RepoModule.class,
		DatabaseModule.class,
		TestServiceModule.class,
	}
)
public interface TestObjectComponent extends ObjectComponent, TestInjector {
}
