package io.explod.testable.util.mvp.fragment;

import android.support.annotation.CallSuper;

import io.explod.testable.util.mvp.base.BaseRxPresenter;

public abstract class BaseFragmentPresenter<V extends BaseFragmentView> extends BaseRxPresenter<V> {

	@CallSuper
	public void onResume() {
	}

	@CallSuper
	public void onPause() {
	}

	@CallSuper
	public void onStop() {
	}

	@CallSuper
	public void onDestroy() {
	}

}
