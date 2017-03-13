package meta.module;


import javax.inject.Singleton;

import dagger.Component;
import io.explod.testable.module.ObjectComponent;
import io.explod.testable.module.modules.AppModule;

@Singleton
@Component(
	modules = {
		AppModule.class,
	}
)
public interface TestObjectComponent extends ObjectComponent, TestInjector {
}
