package io.explod.testable.data.remote;


import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.explod.testable.data.local.LocalDatabase;
import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import io.explod.testable.data.remote.model.UserRepositoryResponse;
import io.reactivex.Observable;
import io.reactivex.Single;

import static io.explod.testable.module.ObjectGraph.getInjector;

public class AppRemoteRepo {

	@Inject
	GithubService mGithubService;

	@Inject
	LocalDatabase mLocalDatabase;

	public AppRemoteRepo() {
		getInjector().inject(this);
	}


	@NonNull
	public Single<Pair<User, List<Repository>>> getRepositories(@NonNull String username) {

		Single<User> userCreate = mLocalDatabase.users().getOrCreate(username);
		Single<List<UserRepositoryResponse>> reposFetch = mGithubService.getUserRepos(username);

		return Single.zip(userCreate, reposFetch, Pair::create)
			.flatMapObservable(userRepos -> saveRepositories(userRepos.first, userRepos.second))
			.collect(
				// collect the results in a mutable pair
				// the pair needs to be mutable so that we can save the user when the collector
				// knows who it is
				UserRepos::new,
				(results, next) -> {
					// save the user because we now know who it is
					results.user = next.first;
					results.repos.add(next.second);
				}
			)
			.map(userRepos -> Pair.create(userRepos.user, userRepos.repos));
	}

	private Observable<Pair<User, Repository>> saveRepositories(@NonNull User user, @NonNull List<UserRepositoryResponse> repos) {
		return Observable.fromIterable(repos)
			.flatMapSingle(repo -> mLocalDatabase.repositories().getOrCreate(user.getId(), repo.name))
			.map(repo -> Pair.create(user, repo));
	}

	// intentionally mutable for a collect call
	// so we can modify the user when we know who it is
	private static class UserRepos {
		User user;
		List<Repository> repos = new ArrayList<>();
	}
}
