package io.explod.testable;

import android.app.Application;
import android.support.annotation.NonNull;

import io.explod.testable.module.ObjectComponent;

import static io.explod.testable.module.ObjectGraph.setObjectComponent;


public abstract class App extends Application {

	private static App sInstance;

	public static App get() {
		return sInstance;
	}

	@NonNull
	protected abstract ObjectComponent buildObjectComponent();

	@Override
	public void onCreate() {
		super.onCreate();
		sInstance = this;
		setObjectComponent(buildObjectComponent());
	}
}
