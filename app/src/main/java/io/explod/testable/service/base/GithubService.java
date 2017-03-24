package io.explod.testable.service.base;


import android.support.annotation.NonNull;

import java.util.List;

import io.explod.testable.data.remote.model.UserRepositoryResponse;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GithubService {

	String ACCEPT_JSON = "Accept: application/json";

	@NonNull
	@GET("/users/{username}/repos")
	@Headers(ACCEPT_JSON)
	Single<Response<List<UserRepositoryResponse>>> getUserReposRaw(@Path("username") String username);

}
