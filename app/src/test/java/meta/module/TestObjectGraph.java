package meta.module;

import android.support.annotation.NonNull;

import static io.explod.testable.module.ObjectGraph.getInjector;

public class TestObjectGraph {

	@NonNull
	public static TestInjector getTestInjector() {
		return (TestInjector) getInjector();
	}

}
