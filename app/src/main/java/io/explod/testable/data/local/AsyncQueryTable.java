package io.explod.testable.data.local;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;

import com.fernandocejas.arrow.optional.Optional;

import java.util.List;

import io.explod.querydb.table.QueryTable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static io.explod.testable.util.rx.OptionalUtils.optional;

/**
 * Wrapper for {@link QueryTable} that runs actions on Singles subscribed to a particular scheduler.
 *
 * @param <T> See {@link QueryTable}
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class AsyncQueryTable<T> {

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
	@NonNull
	public Single<List<T>> getAll() {
		return Single.fromCallable(mQueryTable::getAll)
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getAll(String, String...)
	 */
	@NonNull
	public Single<List<T>> getAll(@Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.getAll(where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getAllSorted(String, String, String...)
	 */
	@NonNull
	public Single<List<T>> getAllSorted(@Nullable String sort, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.getAllSorted(sort, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getAll(SQLiteDatabase, String, String, String...)
	 */
	@NonNull
	public Single<List<T>> getAll(@NonNull SQLiteDatabase db, @Nullable String sort, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.getAll(db, sort, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#first()
	 */
	@NonNull
	public Single<Optional<T>> first() {
		return Single.fromCallable(() -> optional(mQueryTable.first()))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#first(String, String...)
	 */
	@NonNull
	public Single<Optional<T>> first(@Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> optional(mQueryTable.first(where, whereArgs)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#firstSorted(String, String, String...)
	 */
	@NonNull
	public Single<Optional<T>> first(@Nullable String sort, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> optional(mQueryTable.firstSorted(sort, where, whereArgs)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#firstSorted(SQLiteDatabase, String, String, String...)
	 */
	@NonNull
	public Single<Optional<T>> firstSorted(@NonNull SQLiteDatabase db, @Nullable String sort, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> optional(mQueryTable.firstSorted(db, sort, where, whereArgs)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#byId(long)
	 */
	@NonNull
	public Single<Optional<T>> byId(long id) {
		return Single.fromCallable(() -> optional(mQueryTable.byId(id)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#byId(SQLiteDatabase, long)
	 */
	@NonNull
	public Single<Optional<T>> byId(@NonNull SQLiteDatabase db, long id) {
		return Single.fromCallable(() -> optional(mQueryTable.byId(db, id)))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#exists(String, String...)
	 */
	@NonNull
	public Single<Boolean> exists(@Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.exists(where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#exists(SQLiteDatabase, String, String...)
	 */
	@NonNull
	public Single<Boolean> exists(@NonNull SQLiteDatabase db, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.exists(db, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#count()
	 */
	@NonNull
	public Single<Long> count() {
		return Single.fromCallable(mQueryTable::count)
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#count(String, String...)
	 */
	@NonNull
	public Single<Long> count(@Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.count(where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#count(SQLiteDatabase, String, String...)
	 */
	@NonNull
	public Single<Long> count(@NonNull SQLiteDatabase db, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.count(db, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#insert(ContentValues)
	 */
	@NonNull
	public Single<Long> insert(@NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.insert(values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#insert(SQLiteDatabase, ContentValues)
	 */
	@NonNull
	public Single<Long> insert(@NonNull SQLiteDatabase db, @NonNull ContentValues values) {
		return Single.fromCallable(() -> mQueryTable.insert(db, values))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#update(ContentValues, String, String...)
	 */
	@NonNull
	public Single<Long> update(@NonNull ContentValues values, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.update(values, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#update(SQLiteDatabase, ContentValues, String, String...)
	 */
	@NonNull
	public Single<Long> update(@NonNull SQLiteDatabase db, @NonNull ContentValues values, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.update(db, values, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#delete()
	 */
	@NonNull
	public Single<Long> delete() {
		return Single.fromCallable(mQueryTable::delete)
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#delete(String, String...)
	 */
	@NonNull
	public Single<Long> delete(@Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.delete(where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#delete(SQLiteDatabase, String, String...)
	 */
	@NonNull
	public Single<Long> delete(@NonNull SQLiteDatabase db, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.delete(db, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#upsert(ContentValues, String, String...)
	 */
	@NonNull
	public Single<Long> upsert(@NonNull ContentValues values, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.upsert(values, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#upsert(SQLiteDatabase, ContentValues, String, String...)
	 */
	@NonNull
	public Single<Long> upsert(@NonNull SQLiteDatabase db, @NonNull ContentValues values, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.upsert(db, values, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getOrCreate(ContentValues, String, String...)
	 */
	@NonNull
	public Single<T> getOrCreate(@NonNull ContentValues values, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.getOrCreate(values, where, whereArgs))
			.subscribeOn(mScheduler);
	}

	/**
	 * @see QueryTable#getOrCreate(SQLiteDatabase, ContentValues, String, String...)
	 */
	@NonNull
	public Single<T> getOrCreate(@NonNull SQLiteDatabase db, @NonNull ContentValues values, @Nullable String where, @Nullable @Size(min = 0) String... whereArgs) {
		return Single.fromCallable(() -> mQueryTable.getOrCreate(db, values, where, whereArgs))
			.subscribeOn(mScheduler);
	}

}
