package meta.service;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.explod.testable.data.remote.GithubService;
import io.explod.testable.data.remote.model.UserRepositoryResponse;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Path;

public class TestGithubService implements GithubService {

	private static <T> Single<T> network(@NonNull T response) {
		return Single.just(response)
			.delay(50L + ((long) (Math.random() * 100)), TimeUnit.MILLISECONDS)
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	@Override
	public Single<List<UserRepositoryResponse>> getUserRepos(@Path("username") String username) {
		List<UserRepositoryResponse> response = new ArrayList<>();

		for (String repo : new String[]{"a", "b", "c"}) {
			UserRepositoryResponse part = new UserRepositoryResponse();
			part.name = username + "-" + repo;
			part.description = "sample description";
			part.owner = new UserRepositoryResponse.Owner();
			part.owner.username = username;
			response.add(part);
		}

		return network(response);
	}
}