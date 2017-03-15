package io.explod.testable.module.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.explod.testable.App;
import io.explod.testable.data.local.LocalDatabase;

@Module
public class DatabaseModule {

	@Provides
	@NonNull
	@Singleton
	LocalDatabase providesAppLocalDatabase(@NonNull App app) {
		return new LocalDatabase(app);
	}

}
