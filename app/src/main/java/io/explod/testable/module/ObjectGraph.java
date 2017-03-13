package io.explod.testable.module;

import android.support.annotation.NonNull;

public class ObjectGraph {

	private static ObjectComponent sObjectComponent;

	public static Injector getInjector() {
		return sObjectComponent;
	}

	public static void setObjectComponent(@NonNull ObjectComponent component) {
		sObjectComponent = component;
	}

}
