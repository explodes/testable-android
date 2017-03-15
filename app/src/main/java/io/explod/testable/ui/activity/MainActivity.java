package io.explod.testable.ui.activity;

import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import io.explod.testable.R;
import io.explod.testable.data.remote.AppRemoteRepo;

import static io.explod.testable.module.ObjectGraph.getInjector;
import static io.explod.testable.util.TagUtils.makeTag;

public class MainActivity extends BaseActivity {

	private static final String TAG = makeTag(MainActivity.class);

	@Inject
	AppRemoteRepo mAppRemoteRepo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getInjector().inject(this);
		setContentView(R.layout.activity_main);

		mAppRemoteRepo.getRepositories("explodes")
			.subscribe(
				t -> Log.d(TAG, "Got repo for user #" + t.getB().getId() + ": " + t.getA().getName() + " called " + t.getB().getName()),
				e -> Log.e(TAG, "Error fetching repos", e)
			);
	}
}
