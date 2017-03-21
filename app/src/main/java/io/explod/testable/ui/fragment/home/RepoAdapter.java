package io.explod.testable.ui.fragment.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.explod.testable.R;
import io.explod.testable.data.local.model.Repository;

class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

	public interface OnClick {
		void onClick(int position, @NonNull Repository repo);
	}

	@Nullable
	private OnClick mClickListener;

	private List<Repository> mRepositories;

	public RepoAdapter() {
		setHasStableIds(true);
	}

	public void setClickListener(@Nullable OnClick clickListener) {
		mClickListener = clickListener;
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

		holder.titleText.setText(repo.getName());
		holder.descriptionText.setText(repo.getDescription());

		Context context = holder.itemView.getContext();
		if (context == null) return;

		List<String> stats = new ArrayList<>();
		if (repo.getForks() > 0) {
			stats.add(context.getString(R.string.repo_stats_forks, repo.getForks()));
		}
		if (repo.getWatchers() > 0) {
			stats.add(context.getString(R.string.repo_stats_watchers, repo.getWatchers()));
		}
		if (repo.getStars() > 0) {
			stats.add(context.getString(R.string.repo_stats_stars, repo.getStars()));
		}
		if (stats.size() == 0) {
			holder.statsText.setText(null);
		} else {
			holder.statsText.setText(TextUtils.join(context.getString(R.string.repo_stats_sep), stats));
		}
	}

	@Override
	public int getItemCount() {
		return mRepositories == null ? 0 : mRepositories.size();
	}

	@Override
	public long getItemId(int position) {
		return mRepositories.get(position).getId();
	}

	private void fireOnClick(int position) {
		if (mClickListener != null) {
			Repository repo = mRepositories.get(position);
			if (repo != null) {
				mClickListener.onClick(position, repo);
			}
		}
	}

	class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		@BindView(R.id.text_repository_title)
		TextView titleText;

		@BindView(R.id.text_repository_description)
		TextView descriptionText;

		@BindView(R.id.text_repository_stats)
		TextView statsText;

		RepoViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			fireOnClick(getAdapterPosition());
		}
	}

}
