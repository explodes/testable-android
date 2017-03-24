package meta;

import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.explod.testable.BuildConfig;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class, sdk = Config.ALL_SDKS)
public abstract class BaseRoboTest {

	@Before
	public void setUpDexcache() {
		System.setProperty("dexmaker.dexcache", RuntimeEnvironment.application.getCacheDir().getPath());
	}

	protected static void waitForMainLooper(@NonNull Context context) {
		org.robolectric.util.Scheduler scheduler = shadowOf(context.getMainLooper()).getScheduler();
		while (!scheduler.advanceToLastPostedRunnable()) ;
	}
}
