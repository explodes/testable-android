package io.explod.testable.ui.fragment.home;

import android.support.annotation.NonNull;

import io.explod.testable.ui.fragment.BaseFragment;

public class HomeFragment extends BaseFragment<HomeFragmentView, HomeFragmentPresenter>
	implements HomeFragmentView {

	@NonNull
	@Override
	public HomeFragmentPresenter createPresenter() {
		return new HomeFragmentPresenter();
	}
}
