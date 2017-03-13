package io.explod.testable.ui.activity;

import android.os.Bundle;

import javax.inject.Inject;

import io.explod.testable.R;
import io.explod.testable.data.local.AppLocalRepo;

import static io.explod.testable.module.ObjectGraph.getInjector;
import static io.explod.testable.util.TagUtils.makeTag;

public class MainActivity extends BaseActivity {

	private static final String TAG = makeTag(MainActivity.class);

	@Inject
	AppLocalRepo mAppLocalRepo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getInjector().inject(this);
		setContentView(R.layout.activity_main);
	}
}
