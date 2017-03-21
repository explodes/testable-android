package io.explod.testable.ui.fragment.home;

import android.support.annotation.NonNull;

import java.util.List;

import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import io.explod.testable.util.mvp.fragment.BaseFragmentView;

interface HomeFragmentView extends BaseFragmentView {

	void setUser(@NonNull User user);

	void setRepositories(@NonNull List<Repository> repos);

	void showError(@NonNull Throwable t);

}
