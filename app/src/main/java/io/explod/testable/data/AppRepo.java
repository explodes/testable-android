package io.explod.testable.data;


import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;

import javax.inject.Inject;

import io.explod.testable.data.local.AppDatabase;
import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import io.explod.testable.data.remote.GithubService;
import io.explod.testable.data.remote.model.UserRepositoryResponse;
import io.reactivex.Observable;
import io.reactivex.Single;

import static io.explod.testable.module.ObjectGraph.getInjector;

public class AppRepo {

	@Inject
	GithubService mGithubService;

	@Inject
	AppDatabase mAppDatabase;

	public AppRepo() {
		getInjector().inject(this);
	}

	@NonNull
	public OfflineFirstObservable<Pair<User, List<Repository>>> getRepositories(@NonNull String username) {
		return new GetRepositoriesByUsername().getOfflineFirstObservable(username);
	}

	private class GetRepositoriesByUsername {

		@NonNull
		OfflineFirstObservable<Pair<User, List<Repository>>> getOfflineFirstObservable(@NonNull String username) {
			return OfflineFirstObservable.from(
				getRepositoriesOffline(username),
				// get from online, save them, and return our offline results
				getRepositoriesOnline(username).flatMap(ignored -> getRepositoriesOffline(username))
			);
		}

		@NonNull
		private Single<Pair<User, List<Repository>>> getRepositoriesOffline(@NonNull String username) {
			return mAppDatabase.users()
				.getOrCreate(username)
				.flatMap(user -> mAppDatabase.repositories()
					.getAllForUser(user.getId())
					.map(repos -> Pair.create(user, repos))
				);
		}

		@NonNull
		private Single<Object> getRepositoriesOnline(@NonNull String username) {
			Single<User> userCreate = mAppDatabase.users().getOrCreate(username);
			Single<List<UserRepositoryResponse>> reposFetch = mGithubService.getUserRepos(username);

			return Single.zip(userCreate, reposFetch, Pair::create)
				.flatMapObservable(userRepos -> saveRepositories(userRepos.first, userRepos.second))
				.collect(() -> Boolean.TRUE, (a, b) -> {
				});
		}

		@NonNull
		private Observable<Object> saveRepositories(@NonNull User user, @NonNull List<UserRepositoryResponse> repos) {
			return Observable.fromIterable(repos)
				.flatMapSingle(repo -> mAppDatabase.repositories().getOrCreate(user.getId(), repo.name));
		}
	}
}
