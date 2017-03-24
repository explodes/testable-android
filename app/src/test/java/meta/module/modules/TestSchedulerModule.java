package meta.module.modules;


import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.explod.testable.module.modules.SchedulerModule;
import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.ExecutorScheduler;

@Module
public class TestSchedulerModule {

	private static class ImmediateScheduler extends Scheduler {
		@NonNull
		@Override
		public Worker createWorker() {
			return new ExecutorScheduler.ExecutorWorker(Runnable::run);
		}
	}

	private static final ImmediateScheduler sImmediateScheduler = new ImmediateScheduler();

	@NonNull
	@Singleton
	@Provides
	@Named(SchedulerModule.ASYNC_QUERY_SCHEDULER)
	Scheduler providesAsyncQueryScheduler() {
		return sImmediateScheduler;
	}

}
