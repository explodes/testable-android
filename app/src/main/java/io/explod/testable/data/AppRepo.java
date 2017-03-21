package io.explod.testable.data;


import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;

import javax.inject.Inject;

import io.explod.testable.data.local.AppDatabase;
import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import io.explod.testable.data.remote.GithubService;
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

		Single<Pair<User, List<Repository>>> offline = mAppDatabase.users()
			.getOrCreate(username)
			.flatMap(user -> mAppDatabase.repositories()
				.getAllForUser(user.getId())
				.map(repos -> Pair.create(user, repos))
			);

		Single<Pair<User, List<Repository>>> online = Single.zip(mAppDatabase.users().getOrCreate(username), mGithubService.getUserRepos(username), Pair::create)
			.flatMap(userRepos -> Observable.fromIterable(userRepos.second)
				.flatMapSingle(repo -> mAppDatabase.repositories().upsert(userRepos.first.getId(), repo.name, repo.description == null ? "" : repo.description, repo.forks, repo.stars, repo.watchers))
				.count())
			.flatMap(ignored -> offline);

		return OfflineFirstObservable.from(offline, online);

	}

}
