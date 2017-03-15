package io.explod.testable.module.modules;


import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.explod.testable.data.remote.GithubService;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceModule {

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

}
