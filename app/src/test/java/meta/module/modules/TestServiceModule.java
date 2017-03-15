package meta.module.modules;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.explod.testable.data.remote.GithubService;
import io.explod.testable.data.remote.model.UserRepositoryResponse;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Path;

@Module
public class TestServiceModule {

	@Provides
	@NonNull
	@Singleton
	GithubService providesGithubService() {
		return new GithubService() {

			private <T> Single<T> network(T response) {
				return Single.just(response)
					.delay(50L + ((long) (Math.random() * 100)), TimeUnit.MILLISECONDS)
					.subscribeOn(Schedulers.io());
			}

			@NonNull
			@Override
			public Single<List<UserRepositoryResponse>> getUserRepos(@Path("username") String username) {
				List<UserRepositoryResponse> response = new ArrayList<>();

				for (int index = 0; index < 10; index++) {
					UserRepositoryResponse part = new UserRepositoryResponse();
					part.name = "foo-repo-" + index;
					part.owner = new UserRepositoryResponse.Owner();
					part.owner.username = "mock-user";
					response.add(part);
				}

				return network(response);
			}
		};
	}
}
