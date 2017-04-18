package meta.rx;

import android.support.annotation.NonNull;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;

public class ImmediateSchedulerRule implements TestRule {

	private final Scheduler immediate = new ImmediateScheduler();

	@NonNull
	@Override
	public Statement apply(@NonNull final Statement base, Description d) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				RxJavaPlugins.setIoSchedulerHandler(scheduler -> immediate);
				RxJavaPlugins.setComputationSchedulerHandler(scheduler -> immediate);
				RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> immediate);
				RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> immediate);
				try {
					base.evaluate();
				} finally {
					RxJavaPlugins.reset();
					RxAndroidPlugins.reset();
				}
			}
		};
	}
}
