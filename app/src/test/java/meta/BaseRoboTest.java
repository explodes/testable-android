package meta;

import android.content.Context;
import android.support.annotation.NonNull;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.explod.testable.BuildConfig;

@Config(constants = BuildConfig.class, application = TestApp.class)
public abstract class BaseRoboTest {

	@NonNull @Rule
	public TestName name = new TestName();

	@NonNull
	protected Context getContext() {
		Context context = RuntimeEnvironment.application;
		if (context == null) throw new NullPointerException("context not available");
		return context;
	}

	protected void println(@NonNull String format, @NonNull Object... args) {
		String formatted = String.format(format, args);
		String fqdn = String.format("%s: %s", name.getMethodName(), formatted);
		System.out.println(fqdn);
	}
}
