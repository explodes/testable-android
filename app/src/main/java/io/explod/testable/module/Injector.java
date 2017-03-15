package io.explod.testable.module;

import io.explod.testable.data.local.LocalGithubRepositoryRepo;
import io.explod.testable.data.local.LocalGithubUserRepo;
import io.explod.testable.data.remote.AppRemoteRepo;
import io.explod.testable.ui.activity.MainActivity;

public interface Injector {
	void inject(MainActivity target);

	void inject(AppRemoteRepo target);

	void inject(LocalGithubUserRepo target);

	void inject(LocalGithubRepositoryRepo target);

}