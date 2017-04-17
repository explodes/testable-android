package meta;

import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.Scheduler;

import io.explod.testable.BuildConfig;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class)
public abstract class BaseRoboTest {

    @Before
    public void setUpDexcache() {
        System.setProperty("dexmaker.dexcache", RuntimeEnvironment.application.getCacheDir().getPath());
    }

    protected static void waitForMainLooper() {
        Context context = RuntimeEnvironment.application;

        Scheduler scheduler = shadowOf(context.getMainLooper()).getScheduler();
        while (scheduler.advanceToLastPostedRunnable()) ;
    }
}
