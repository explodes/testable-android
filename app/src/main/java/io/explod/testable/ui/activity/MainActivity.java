package io.explod.testable.ui.activity;

import android.os.Bundle;

import javax.inject.Inject;

import io.explod.testable.R;
import io.explod.testable.data.AppRepo;
import io.explod.testable.ui.fragment.home.HomeFragment;

import static io.explod.testable.module.ObjectGraph.getInjector;

public class MainActivity extends BaseActivity {

	@Inject
	AppRepo mAppRepo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getInjector().inject(this);
		setContentView(R.layout.activity_main);

		getSupportFragmentManager()
			.beginTransaction()
			.replace(R.id.container, HomeFragment.newInstance())
			.commit();
	}
}
