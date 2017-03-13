package io.explod.testable.data.local;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.explod.testable.data.local.contract.GithubRepositoryContract;
import io.explod.testable.data.local.contract.GithubUserContract;
import io.explod.testable.data.local.model.GithubRepository;
import io.explod.testable.data.local.model.GithubUser;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static io.explod.testable.module.ObjectGraph.getInjector;
import static io.explod.testable.util.ObservableUtils.asObservable;
import static io.explod.testable.util.ObservableUtils.eachRow;

public class AppLocalRepo {

	@Inject
	AppLocalDatabase mDatabase;

	public AppLocalRepo() {
		getInjector().inject(this);
	}

	@NonNull
	public Observable<GithubUser> getUsers() {
		return asObservable(() ->
			mDatabase.select()
				.table(GithubUserContract.TABLE)
				.columns(GithubUserContract.Projection.ALL)
				.execute())
			.flatMap(cursor -> eachRow(cursor, GithubUser::fromCursor))
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public Observable<Long> insertUser(@NonNull String username) {
		return asObservable(() ->
			mDatabase.insert()
				.table(GithubUserContract.TABLE)
				.value(GithubUserContract.Columns.NAME, username)
				.execute())
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public Observable<GithubRepository> getRepositories() {
		return asObservable(() ->
			mDatabase.select()
				.table(GithubRepositoryContract.TABLE)
				.columns(GithubRepositoryContract.Projection.ALL)
				.execute())
			.flatMap(cursor -> eachRow(cursor, GithubRepository::fromCursor))
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public Observable<Long> insertRepository(long userId, @NonNull String name) {
		return asObservable(() ->
			mDatabase.insert()
				.table(GithubRepositoryContract.TABLE)
				.value(GithubRepositoryContract.Columns.USER_ID, userId)
				.value(GithubRepositoryContract.Columns.NAME, name)
				.execute())
			.subscribeOn(Schedulers.io());
	}

}
