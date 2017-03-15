package io.explod.testable.data.remote;


import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.explod.testable.data.local.AppLocalRepo;
import io.explod.testable.data.local.model.GithubRepository;
import io.explod.testable.data.local.model.GithubUser;
import io.explod.testable.data.remote.model.GithubUserRepositoryResponse;
import io.explod.testable.util.rx.tuple.Tuple2;
import io.reactivex.Observable;
import io.reactivex.Single;

import static io.explod.testable.module.ObjectGraph.getInjector;

public class AppRemoteRepo {

	@Inject
	GithubService mGithubService;

	@Inject
	AppLocalRepo mAppLocalRepo;

	public AppRemoteRepo() {
		getInjector().inject(this);
	}


	@NonNull
	public Observable<Tuple2<GithubUser, GithubRepository>> getRepositories(@NonNull String username) {

		Single<GithubUser> userCreate = mAppLocalRepo.Users.getOrCreate(username);
		Observable<GithubUserRepositoryResponse> reposFetch = mGithubService.getUserRepos(username)
			.flatMap(Observable::fromIterable);

		return Observable.combineLatest(userCreate.toObservable(), reposFetch, Tuple2::of)
			.flatMap(tuple -> {
				GithubUser user = tuple.getA();
				GithubUserRepositoryResponse repoInfo = tuple.getB();
				return mAppLocalRepo.Repositories.getOrCreate(user.getId(), repoInfo.name).map(repo -> Tuple2.of(user, repo)).toObservable();
			});
	}

}
