package io.explod.testable.data;


import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.explod.testable.data.local.AppDatabase;
import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import io.explod.testable.data.remote.model.UserRepositoryResponse;
import io.explod.testable.service.base.GithubService;
import io.reactivex.Observable;
import io.reactivex.Single;

import static io.explod.testable.data.remote.LinkFollower.followNext;
import static io.explod.testable.module.ObjectGraph.getInjector;

public class AppRepo {

	private static final TypeToken<List<UserRepositoryResponse>> sListOfUserRepositoryResponseToken = new TypeToken<List<UserRepositoryResponse>>() {
	};

	@Inject
	GithubService mGithubService;

	@Inject
	AppDatabase mAppDatabase;

	public AppRepo() {
		getInjector().inject(this);
	}

	@NonNull
	public OfflineFirstObservable<Pair<User, List<Repository>>> getRepositories(@NonNull String username) {

		Single<Pair<User, List<Repository>>> offline = mAppDatabase.users()
			.getOrCreate(username)
			.flatMap(user -> mAppDatabase.repositories()
				.getAllForUser(user.getId())
				.map(repos -> Pair.create(user, repos))
			);

		// Github's api has the next pages' urls embedded in the "Link" response header.
		// followNext is the utility to fetch successive pages
		Single<List<UserRepositoryResponse>> allOnlineRepos = followNext(mGithubService.getUserReposRaw(username), sListOfUserRepositoryResponseToken, new ArrayList<>(), List::addAll);

		Single<Pair<User, List<Repository>>> online = Single.zip(mAppDatabase.users().getOrCreate(username), allOnlineRepos, Pair::create)
			.flatMap(userRepos -> Observable.fromIterable(userRepos.second)
				.flatMapSingle(repo -> mAppDatabase.repositories().upsert(userRepos.first.getId(), repo.name, repo.description == null ? "" : repo.description, repo.forks, repo.stars, repo.watchers))
				.count())
			.flatMap(ignored -> offline);

		return OfflineFirstObservable.from(offline, online);

	}
}
