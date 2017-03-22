package meta.service;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.explod.testable.data.remote.GithubService;
import io.explod.testable.data.remote.model.UserRepositoryResponse;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.http.Path;

public class TestGithubService implements GithubService {

	private static <T> Single<T> network(@NonNull T response) {
		return Single.just(response)
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	@Override
	public Single<List<UserRepositoryResponse>> getUserRepos(@Path("username") String username) {
		List<UserRepositoryResponse> repos = createUserRepositoryResponses(username);
		return network(repos);
	}

	@NonNull
	@Override
	public Single<Response<List<UserRepositoryResponse>>> getUserReposRaw(@Path("username") String username) {
		List<UserRepositoryResponse> repos = createUserRepositoryResponses(username);
		Response<List<UserRepositoryResponse>> response = Response.success(repos);
		return network(response);
	}

	@NonNull
	private List<UserRepositoryResponse> createUserRepositoryResponses(@Path("username") String username) {
		List<UserRepositoryResponse> repos = new ArrayList<>();

		for (String repo : new String[]{"a", "b", "c"}) {
			UserRepositoryResponse part = new UserRepositoryResponse();
			part.name = username + "-" + repo;
			part.description = "sample description";
			part.owner = new UserRepositoryResponse.Owner();
			part.owner.username = username;
			repos.add(part);
		}
		return repos;
	}

}