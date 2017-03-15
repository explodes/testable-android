package meta.module.modules;


import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.explod.testable.data.remote.GithubService;
import io.explod.testable.service.base.InternetConnectivityService;

@Module
public class TestServiceModule {

	@Provides
	@NonNull
	@Singleton
	GithubService providesGithubService() {
		return new TestGithubService();
	}

	@Provides
	@NonNull
	@Singleton
	InternetConnectivityService providesInternetConnectivityService() {
		return new TestInternetConnectivityService();
	}

}
