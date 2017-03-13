package io.explod.testable.module;

import io.explod.testable.data.local.AppLocalRepo;
import io.explod.testable.ui.activity.MainActivity;

public interface Injector {
	void inject(AppLocalRepo target);

	void inject(MainActivity target);

}