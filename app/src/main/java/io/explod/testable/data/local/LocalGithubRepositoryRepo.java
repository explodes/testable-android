package io.explod.testable.data.local;


import android.support.annotation.NonNull;
import android.util.Log;

import com.fernandocejas.arrow.optional.Optional;

import javax.inject.Inject;

import io.explod.testable.data.local.contract.GithubRepositoryContract;
import io.explod.testable.data.local.exceptions.InsertFailedException;
import io.explod.testable.data.local.exceptions.SelectFailedException;
import io.explod.testable.data.local.model.GithubRepository;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static io.explod.testable.module.ObjectGraph.getInjector;
import static io.explod.testable.util.ObservableUtils.eachRow;
import static io.explod.testable.util.ObservableUtils.firstRow;
import static io.explod.testable.util.TagUtils.makeTag;

public class LocalGithubRepositoryRepo {

	private static final String TAG = makeTag(LocalGithubRepositoryRepo.class);

	@Inject
	AppLocalDatabase mDatabase;

	public LocalGithubRepositoryRepo() {
		getInjector().inject(this);
	}

	@NonNull
	public Observable<GithubRepository> getAll() {
		Log.d(TAG, "getAll() called");
		return Observable.fromCallable(() ->
			mDatabase.select()
				.table(GithubRepositoryContract.TABLE)
				.columns(GithubRepositoryContract.Projection.ALL)
				.execute())
			.flatMap(cursor -> eachRow(cursor, GithubRepository::fromCursor))
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public Single<Optional<GithubRepository>> byId(long id) {
		Log.d(TAG, "byId() called with: id = [" + id + "]");
		return Observable.fromCallable(() ->
			mDatabase.select()
				.table(GithubRepositoryContract.TABLE)
				.columns(GithubRepositoryContract.Projection.ALL)
				.byId(id)
				.execute())
			.flatMap(cursor -> firstRow(cursor, GithubRepository::fromCursor))
			.subscribeOn(Schedulers.io())
			.singleOrError();
	}

	@NonNull
	public Single<Optional<GithubRepository>> get(long userId, @NonNull String name) {
		Log.d(TAG, "get() called with: userId = [" + userId + "], name = [" + name + "]");
		return Observable.fromCallable(() ->
			mDatabase.select()
				.table(GithubRepositoryContract.TABLE)
				.columns(GithubRepositoryContract.Projection.ALL)
				.where(String.format("%s = ? AND %s = ?", GithubRepositoryContract.Columns.USER_ID, GithubRepositoryContract.Columns.NAME), String.valueOf(userId), name)
				.execute())
			.flatMap(cursor -> firstRow(cursor, GithubRepository::fromCursor))
			.subscribeOn(Schedulers.io())
			.singleOrError();
	}

	@NonNull
	public Single<GithubRepository> insert(long userId, @NonNull String name) {
		Log.d(TAG, "insert() called with: userId = [" + userId + "], name = [" + name + "]");
		return Single.fromCallable(() ->
			mDatabase.insert()
				.table(GithubRepositoryContract.TABLE)
				.value(GithubRepositoryContract.Columns.USER_ID, userId)
				.value(GithubRepositoryContract.Columns.NAME, name)
				.execute())
			.subscribeOn(Schedulers.io())
			.flatMap(id -> {
				if (id == -1)
					return Single.error(new InsertFailedException("Insert failed for GithubRepository"));
				return byId(id).flatMap(o -> {
					if (o.isPresent()) return Single.just(o.get());
					return Single.error(new SelectFailedException("Unable to fetch GithubRepository"));
				});
			})
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public Single<GithubRepository> getOrCreate(long userId, @NonNull String name) {
		return get(userId, name)
			.flatMap(o -> {
				if (o.isPresent()) return Single.just(o.get());
				return insert(userId, name);
			});
	}
}
