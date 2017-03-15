package meta;

import org.robolectric.annotation.Config;

import io.explod.testable.BuildConfig;

@Config(constants = BuildConfig.class, application = TestApp.class)
public abstract class BaseRoboTest {
}
