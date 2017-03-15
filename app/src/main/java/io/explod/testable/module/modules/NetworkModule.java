package io.explod.testable.module.modules;


import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.explod.testable.App;
import io.explod.testable.BuildConfig;
import io.explod.testable.data.remote.GithubService;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

	private static final String NETWORK_CACHE_DIR = "net";
	private static final long NETWORK_CACHE_SIZE = (1024L * 1024L * 25L);

	@Provides
	@NonNull
	@Singleton
	GithubService providesGithubService(@NonNull Gson gson, @NonNull OkHttpClient client) {
		return new Retrofit.Builder()
			.baseUrl("https://api.github.com/")
			.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(client)
			.build()
			.create(GithubService.class);
	}

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
