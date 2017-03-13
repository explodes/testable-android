package io.explod.testable.util.mvp.fragment;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

public abstract class BaseMvpFragment<V extends BaseFragmentView, P extends BaseFragmentPresenter<V>> extends MvpFragment<V, P> {

	@Override
	public void onResume() {
		super.onResume();
		presenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		presenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		presenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.onDestroy();
	}
}
