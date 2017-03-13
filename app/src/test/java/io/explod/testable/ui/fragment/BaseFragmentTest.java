package io.explod.testable.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import io.explod.testable.util.mvp.fragment.BaseFragmentPresenter;
import io.explod.testable.util.mvp.fragment.BaseFragmentView;
import meta.BaseRoboTest;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static meta.FragUtils.resumeFragment;

@RunWith(RobolectricTestRunner.class)
public class BaseFragmentTest extends BaseRoboTest {


	private interface TestView extends BaseFragmentView {
	}

	private static class TestPresenter extends BaseFragmentPresenter<TestView> {
	}

	public static class TestFrag extends BaseFragment<TestView, TestPresenter> {

		@NonNull
		public static TestFrag newInstance() {
			return new TestFrag();
		}

		@NonNull
		@Override
		public TestPresenter createPresenter() {
			return new TestPresenter();
		}

		@Nullable
		@Override
		public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			return new View(RuntimeEnvironment.application);
		}

	}

	@Test
	public void bindView() throws Exception {
		TestFrag frag = resumeFragment(TestFrag.newInstance());

		frag.bindView(new View(RuntimeEnvironment.application));

		assertNotNull(frag.mUnbinder);
	}

	@Test
	public void onDestroy() throws Exception {
		TestFrag frag = resumeFragment(TestFrag.newInstance());

		frag.bindView(new View(RuntimeEnvironment.application));
		frag.unbindView();

		assertNull(frag.mUnbinder);
	}

}