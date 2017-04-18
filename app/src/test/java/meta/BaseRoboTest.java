package meta;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.explod.testable.BuildConfig;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = TestApp.class)
public abstract class BaseRoboTest {

    @Before
    public void setUpDexcache() {
        System.setProperty("dexmaker.dexcache", RuntimeEnvironment.application.getCacheDir().getPath());
    }

}
