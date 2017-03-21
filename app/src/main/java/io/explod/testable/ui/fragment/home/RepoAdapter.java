package io.explod.testable.ui.fragment.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.explod.testable.R;
import io.explod.testable.data.local.model.Repository;

import static android.content.ContentValues.TAG;

class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

	private List<Repository> mRepositories;

	public RepoAdapter() {
		setHasStableIds(true);
	}

	public void setRepositories(@Nullable List<Repository> repositories) {
		mRepositories = repositories;
		notifyDataSetChanged();
	}

	@Override
	public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();

		View view = LayoutInflater.from(context).inflate(R.layout.item_repository, parent, false);
		return new RepoViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RepoViewHolder holder, int position) {
		Repository repo = mRepositories.get(position);

		Log.d(TAG, "bind " + repo.getName() + " :: " + repo.getDescription());

		holder.titleText.setText(repo.getName());
		holder.descriptionText.setText(repo.getDescription());
	}

	@Override
	public int getItemCount() {
		return mRepositories == null ? 0 : mRepositories.size();
	}

	@Override
	public long getItemId(int position) {
		return mRepositories.get(position).getId();
	}

	static class RepoViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.text_repository_title)
		TextView titleText;

		@BindView(R.id.text_repository_description)
		TextView descriptionText;

		RepoViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}
	}

}
