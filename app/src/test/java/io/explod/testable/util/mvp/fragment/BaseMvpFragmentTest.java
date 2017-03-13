package io.explod.testable.util.mvp.fragment;

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

import meta.BaseRoboTest;

import static meta.FragUtils.destroyFragment;
import static meta.FragUtils.pauseFragment;
import static meta.FragUtils.resumeFragment;
import static meta.FragUtils.stopFragment;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class BaseMvpFragmentTest extends BaseRoboTest {

	private interface TestView extends BaseFragmentView {
	}

	private static class TestPresenter extends BaseFragmentPresenter<TestView> {

		protected boolean mOnResumeCalled;
		protected boolean mOnPauseCalled;
		protected boolean mOnStopCalled;
		protected boolean mOnDestroyCalled;

		@Override
		public void onResume() {
			super.onResume();
			mOnResumeCalled = true;
		}

		@Override
		public void onPause() {
			super.onPause();
			mOnPauseCalled = true;
		}

		@Override
		public void onStop() {
			super.onStop();
			mOnStopCalled = true;
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mOnDestroyCalled = true;
		}
	}

	public static class TestFrag extends BaseMvpFragment<TestView, TestPresenter> {

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
	public void onResumeGetsProxiedToPresenter() {
		TestFrag frag = resumeFragment(TestFrag.newInstance());

		assertTrue(frag.getPresenter().mOnResumeCalled);
	}

	@Test
	public void onPauseGetsProxiedToPresenter() {
		TestFrag frag = pauseFragment(TestFrag.newInstance());

		assertTrue(frag.getPresenter().mOnPauseCalled);
	}

	@Test
	public void onStopGetsProxiedToPresenter() {
		TestFrag frag = stopFragment(TestFrag.newInstance());

		assertTrue(frag.getPresenter().mOnStopCalled);
	}

	@Test
	public void onDestroyGetsProxiedToPresenter() {
		TestFrag frag = destroyFragment(TestFrag.newInstance());

		assertTrue(frag.getPresenter().mOnDestroyCalled);
	}
}