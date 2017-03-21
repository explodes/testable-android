package io.explod.testable.ui.fragment.home;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.List;

import javax.inject.Inject;

import io.explod.testable.data.AppRepo;
import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import io.explod.testable.util.mvp.fragment.BaseFragmentPresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static io.explod.testable.module.ObjectGraph.getInjector;

/**
 * @hide Visible only for injection
 */
public class HomeFragmentPresenter extends BaseFragmentPresenter<HomeFragmentView> {

	private static final String USERNAME = "explodes";

	@Inject
	AppRepo mAppRepo;

	public HomeFragmentPresenter() {
		getInjector().inject(this);
	}

	@Override
	public void onResume() {
		super.onResume();

		mAppRepo.getRepositories(USERNAME)
			.fromOfflineThenOnline()
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				this::onRepositories,
				this::onNetworkError
			);
	}

	private void onRepositories(@NonNull Pair<User, List<Repository>> userRepos) {
		HomeFragmentView view = getView();
		if (view == null) return;

		view.setUser(userRepos.first);
		view.setRepositories(userRepos.second);
	}

	private void onNetworkError(@NonNull Throwable t) {
		HomeFragmentView view = getView();
		if (view == null) return;

		view.showError(t);
	}
}