package meta.rx;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.ExecutorScheduler;

class ImmediateScheduler extends Scheduler {

	@NonNull
	@Override
	public Worker createWorker() {
		return new ExecutorScheduler.ExecutorWorker(Runnable::run);
	}

}
