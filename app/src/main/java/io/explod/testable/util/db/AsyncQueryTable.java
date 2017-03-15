package io.explod.testable.util.db;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fernandocejas.arrow.optional.Optional;

import java.util.List;

import io.explod.querydb.table.QueryTable;
import io.explod.querydb.table.WhereClause;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

/**
 * Wrapper for {@link QueryTable} that runs actions on Singles subscribed to a particular scheduler.
 *
 * @param <T> See {@link QueryTable}
 */
public class AsyncQueryTable<T> {

	@NonNull
	private final QueryTable<T> mQueryTable;

	@NonNull
	private final Scheduler mScheduler;

	public AsyncQueryTable(@NonNull QueryTable<T> wrapped) {
		this(wrapped, Schedulers.io());
	}

	public AsyncQueryTable(@NonNull QueryTable<T> wrapped, @NonNull Scheduler scheduler) {
		mQueryTable = wrapped;
		mScheduler = scheduler;
	}

	@NonNull
	public QueryTable<T> getQueryTable() {
		return mQueryTable;
	}

	/**
	 * @see QueryTable#getAll()
	 */
	public Single<List<T>> getAll() {
		return Single.fromCallable(mQueryTable::getAll)
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getAll(WhereClause)
	 */
	public Single<List<T>> getAll(@Nullable WhereClause where) {
		return Single.fromCallable(() -> mQueryTable.getAll(where))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getAll(WhereClause, String)
	 */
	public Single<List<T>> getAll(@Nullable WhereClause where, @Nullable String sort) {
		return Single.fromCallable(() -> mQueryTable.getAll(where, sort))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getAll(SQLiteDatabase, WhereClause, String)
	 */
	public Single<List<T>> getAll(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @Nullable String sort) {
		return Single.fromCallable(() -> mQueryTable.getAll(db, where, sort))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#first()
	 */
	public Single<Optional<T>> first() {
		return Single.fromCallable(() -> optional(mQueryTable.first()))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#first(WhereClause)
	 */
	public Single<Optional<T>> first(@Nullable WhereClause where) {
		return Single.fromCallable(() -> optional(mQueryTable.first(where)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#first(WhereClause, String)
	 */
	public Single<Optional<T>> first(@Nullable WhereClause where, @Nullable String sort) {
		return Single.fromCallable(() -> optional(mQueryTable.first(where, sort)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#first(SQLiteDatabase, WhereClause, String)
	 */
	public Single<Optional<T>> first(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @Nullable String sort) {
		return Single.fromCallable(() -> optional(mQueryTable.first(db, where, sort)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#byId(long)
	 */
	public Single<Optional<T>> byId(long id) {
		return Single.fromCallable(() -> optional(mQueryTable.byId(id)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#byId(SQLiteDatabase, long)
	 */
	public Single<Optional<T>> byId(@NonNull SQLiteDatabase db, long id) {
		return Single.fromCallable(() -> optional(mQueryTable.byId(db, id)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#exists(WhereClause)
	 */
	public Single<Boolean> exists(@Nullable WhereClause where) {
		return Single.fromCallable(() -> mQueryTable.exists(where))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#exists(SQLiteDatabase, WhereClause)
	 */
	public Single<Boolean> exists(@NonNull SQLiteDatabase db, @Nullable WhereClause where) {
		return Single.fromCallable(() -> mQueryTable.exists(db, where))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#count()
	 */
	public Single<Long> count() {
		return Single.fromCallable(mQueryTable::count)
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#count(WhereClause)
	 */
	public Single<Long> count(@Nullable WhereClause where) {
		return Single.fromCallable(() -> mQueryTable.count(where))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#count(SQLiteDatabase, WhereClause)
	 */
	public Single<Long> count(@NonNull SQLiteDatabase db, @Nullable WhereClause where) {
		return Single.fromCallable(() -> mQueryTable.count(db, where))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#insert(ContentValues)
	 */
	public Single<Long> insert(@NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.insert(values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#insert(SQLiteDatabase, ContentValues)
	 */
	public Single<Long> insert(@NonNull SQLiteDatabase db, @NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.insert(db, values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#update(WhereClause, ContentValues)
	 */
	public Single<Long> update(@Nullable WhereClause where, @NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.update(where, values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#update(SQLiteDatabase, WhereClause, ContentValues)
	 */
	public Single<Long> update(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.update(db, where, values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#delete()
	 */
	public Single<Long> delete() {
		return Single.fromCallable(mQueryTable::delete)
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#delete(WhereClause)
	 */
	public Single<Long> delete(@Nullable WhereClause where) {
		return Single.fromCallable(() -> mQueryTable.delete(where))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#delete(SQLiteDatabase, WhereClause)
	 */
	public Single<Long> delete(@NonNull SQLiteDatabase db, @Nullable WhereClause where) {
		return Single.fromCallable(() -> mQueryTable.delete(db, where))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#upsert(WhereClause, ContentValues)
	 */
	public Single<Long> upsert(@Nullable WhereClause where, @NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.upsert(where, values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#upsert(SQLiteDatabase, WhereClause, ContentValues)
	 */
	public Single<Long> upsert(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.upsert(db, where, values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getOrCreate(WhereClause, ContentValues)
	 */
	public Single<T> getOrCreate(@Nullable WhereClause where, @NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.getOrCreate(where, values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getOrCreate(SQLiteDatabase, WhereClause, ContentValues)
	 */
	public Single<T> getOrCreate(@NonNull SQLiteDatabase db, @Nullable WhereClause where, @NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.getOrCreate(db, where, values))
			.subscribeOn(mScheduler);
	}

	private static <T> Optional<T> optional(@Nullable T t) {
		if (t != null) {
			return Optional.of(t);
		} else {
			return Optional.absent();
		}
	}
}
