package io.explod.testable.ui.fragment;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.explod.testable.util.mvp.fragment.BaseFragmentPresenter;
import io.explod.testable.util.mvp.fragment.BaseFragmentView;
import io.explod.testable.util.mvp.fragment.BaseMvpFragment;

public abstract class BaseFragment<V extends BaseFragmentView, P extends BaseFragmentPresenter<V>> extends BaseMvpFragment<V, P> {

	@Nullable
	@VisibleForTesting
	Unbinder mUnbinder;

	protected void bindView(@Nullable View view) {
		unbindView();
		if (view != null) {
			mUnbinder = ButterKnife.bind(this, view);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unbindView();
	}

	@VisibleForTesting
	void unbindView() {
		if (mUnbinder != null) {
			mUnbinder.unbind();
			mUnbinder = null;
		}
	}
}
