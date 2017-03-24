package io.explod.testable.module.modules;


import android.support.annotation.NonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulerModule {

	public static final String ASYNC_QUERY_SCHEDULER = "async-query";

	private static final String ASYNC_QUERY_SCHEDULER_NAME_PREFIX = "db-thread-";

	@NonNull
	@Singleton
	@Provides
	@Named(ASYNC_QUERY_SCHEDULER)
	Scheduler providesAsyncQueryScheduler() {
		ThreadFactory threadFactory = new NumberedNameThreadFactory(ASYNC_QUERY_SCHEDULER_NAME_PREFIX);
		return Schedulers.from(Executors.newSingleThreadExecutor(threadFactory));
	}

	private static class NumberedNameThreadFactory implements ThreadFactory {

		@NonNull
		private final AtomicInteger count = new AtomicInteger(0);

		@NonNull
		private final String mPrefix;

		NumberedNameThreadFactory(@NonNull String prefix) {
			mPrefix = prefix;
		}

		@NonNull
		private String getNextName() {
			int id = count.addAndGet(1);
			return mPrefix + id;
		}

		@Override
		public Thread newThread(@NonNull Runnable target) {
			return new Thread(target, getNextName());
		}
	}

}
