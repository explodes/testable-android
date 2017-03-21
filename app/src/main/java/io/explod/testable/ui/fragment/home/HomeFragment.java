package io.explod.testable.ui.fragment.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import io.explod.testable.R;
import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import io.explod.testable.ui.fragment.BaseFragment;

public class HomeFragment extends BaseFragment<HomeFragmentView, HomeFragmentPresenter>
	implements HomeFragmentView, RepoAdapter.OnClick {

	@NonNull
	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@BindView(R.id.recycler_home)
	RecyclerView mRecyclerView;

	RepoAdapter mRepoAdapter;

	@NonNull
	@Override
	public HomeFragmentPresenter createPresenter() {
		return new HomeFragmentPresenter();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_home, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (view == null) return;

		Context context = getContext();
		if (context == null) return;

		bindView(view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

		mRepoAdapter = new RepoAdapter();
		mRepoAdapter.setClickListener(this);
		mRecyclerView.setAdapter(mRepoAdapter);
	}

	@Override
	public void setUser(@NonNull User user) {
		Activity activity = getActivity();
		if (activity == null) return;

		activity.setTitle(user.getName());
	}

	@Override
	public void setRepositories(@NonNull List<Repository> repos) {
		if (mRepoAdapter == null) return;
		mRepoAdapter.setRepositories(repos);
	}

	@Override
	public void showError(@NonNull Throwable t) {
		Context context = getContext();
		if (context == null) return;

		Toast.makeText(context, "Error getting repositories: " + t.toString(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(int position, @NonNull Repository repo) {
		presenter.onClickRepo(repo);
	}

	@Override
	public void openUrl(@NonNull String url) {
		Context context = getContext();
		if (context == null) return;

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));

		if (context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
			startActivity(intent);
		}
	}
}
