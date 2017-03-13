package meta.module;

import static io.explod.testable.module.ObjectGraph.getInjector;

public class TestObjectGraph {

	public static TestInjector getTestInjector() {
		return (TestInjector) getInjector();
	}

}
