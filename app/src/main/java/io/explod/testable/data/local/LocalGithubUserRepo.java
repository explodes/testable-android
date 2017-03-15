package io.explod.testable.data.local;


import android.support.annotation.NonNull;
import android.util.Log;

import com.fernandocejas.arrow.optional.Optional;

import javax.inject.Inject;

import io.explod.testable.data.local.contract.GithubUserContract;
import io.explod.testable.data.local.exceptions.InsertFailedException;
import io.explod.testable.data.local.exceptions.SelectFailedException;
import io.explod.testable.data.local.model.GithubUser;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static io.explod.testable.module.ObjectGraph.getInjector;
import static io.explod.testable.util.ObservableUtils.eachRow;
import static io.explod.testable.util.ObservableUtils.firstRow;
import static io.explod.testable.util.TagUtils.makeTag;

public class LocalGithubUserRepo {

	private static final String TAG = makeTag(LocalGithubRepositoryRepo.class);

	@Inject
	AppLocalDatabase mDatabase;

	public LocalGithubUserRepo() {
		getInjector().inject(this);
	}

	@NonNull
	public Observable<GithubUser> getAll() {
		Log.d(TAG, "getAll() called");
		return Observable.fromCallable(() ->
			mDatabase.select()
				.table(GithubUserContract.TABLE)
				.columns(GithubUserContract.Projection.ALL)
				.execute())
			.flatMap(cursor -> eachRow(cursor, GithubUser::fromCursor))
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public Single<Optional<GithubUser>> get(@NonNull String username) {
		Log.d(TAG, "get() called with: username = [" + username + "]");
		return Observable.fromCallable(() ->
			mDatabase.select()
				.table(GithubUserContract.TABLE)
				.columns(GithubUserContract.Projection.ALL)
				.where(String.format("%s = ?", GithubUserContract.Columns.NAME), username)
				.execute())
			.flatMap(cursor -> firstRow(cursor, GithubUser::fromCursor))
			.subscribeOn(Schedulers.io())
			.singleOrError();
	}

	@NonNull
	public Single<GithubUser> insert(@NonNull String username) {
		Log.d(TAG, "insert() called with: username = [" + username + "]");
		return Single.fromCallable(() ->
			mDatabase.insert()
				.table(GithubUserContract.TABLE)
				.value(GithubUserContract.Columns.NAME, username)
				.execute())
			.subscribeOn(Schedulers.io())
			.flatMap(id -> {
				if (id == -1)
					return Single.error(new InsertFailedException("Insert failed for GithubUser"));
				return get(username).flatMap(o -> {
					if (o.isPresent()) return Single.just(o.get());
					return Single.error(new SelectFailedException("Unable to fetch GithubUser"));
				});
			})
			.subscribeOn(Schedulers.io());
	}

	@NonNull
	public Single<GithubUser> getOrCreate(@NonNull String username) {
		Log.d(TAG, "getOrCreate() called with: username = [" + username + "]");
		return get(username)
			.flatMap(o -> {
				if (o.isPresent()) return Single.just(o.get());
				return insert(username);
			});
	}
}
