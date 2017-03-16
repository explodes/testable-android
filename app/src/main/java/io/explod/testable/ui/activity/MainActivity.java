package io.explod.testable.ui.activity;

import android.os.Bundle;

import javax.inject.Inject;

import io.explod.testable.R;
import io.explod.testable.data.AppRepo;

import static io.explod.testable.module.ObjectGraph.getInjector;
import static io.explod.testable.util.TagUtils.makeTag;

public class MainActivity extends BaseActivity {

	private static final String TAG = makeTag(MainActivity.class);

	@Inject
	AppRepo mAppRepo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getInjector().inject(this);
		setContentView(R.layout.activity_main);

//		mAppRepo.getRepositories("explodes")
//			.fromOnlineOrOffline()
//			.flatMapObservable(results -> Observable.fromIterable(results.second).map(repo -> Pair.create(results.first, repo)))
//			.subscribe(
//				userRepo -> Log.d(TAG, "Got repo for user " + userRepo.first.getName() + " (" + userRepo.first.getId() + ") called " + userRepo.second.getName() + " (" + userRepo.second.getId() + ")"),
//				e -> Log.e(TAG, "Error fetching repos", e)
//			);
	}
}
