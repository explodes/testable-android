package io.explod.testable.module.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.explod.testable.data.local.AppLocalRepo;
import io.explod.testable.data.remote.AppRemoteRepo;

@Module
public class RepoModule {

	@Provides
	@NonNull
	@Singleton
	AppLocalRepo providesAppLocalRepo() {
		return new AppLocalRepo();
	}

	@Provides
	@NonNull
	@Singleton
	AppRemoteRepo providesAppRemoteRepo() {
		return new AppRemoteRepo();
	}

}
