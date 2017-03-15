package io.explod.testable.module.modules;


import android.support.annotation.NonNull;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.explod.testable.App;
import io.explod.testable.BuildConfig;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class NetworkModule {

	private static final String NETWORK_CACHE_DIR = "net";
	private static final long NETWORK_CACHE_SIZE = (1024L * 1024L * 25L);

	@Provides
	@NonNull
	@Singleton
	OkHttpClient providesOkHttpClient(@NonNull Cache cache) {

		HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
		logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

		return new OkHttpClient.Builder()
			.cache(cache)
			.addNetworkInterceptor(logger)
			.build();
	}

	@Provides
	@NonNull
	@Singleton
	Cache providesCache(@NonNull App app) {
		File dir = new File(app.getCacheDir(), NETWORK_CACHE_DIR);
		return new Cache(dir, NETWORK_CACHE_SIZE);
	}


}
