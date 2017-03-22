package io.explod.testable.ui.fragment.home;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import io.explod.testable.data.local.model.Repository;
import meta.BaseRoboTest;
import meta.rx.ImmediateSchedulerRule;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomeFragmentPresenterTest extends BaseRoboTest {

	@Rule
	public ImmediateSchedulerRule mImmediateSchedulerRule = new ImmediateSchedulerRule();

	@Mock
	HomeFragmentView view;

	HomeFragmentPresenter presenter;

	@Before
	public void setUp() {
		view = mock(HomeFragmentView.class);

		presenter = new HomeFragmentPresenter();
		presenter.attachView(view);
	}

	@Test
	public void onResumeLoadsOfflineThenOnlineRepositories() {
		presenter.onResume();

		verify(view, times(2)).setRepositories(anyListOf(Repository.class));
	}

	@Test
	public void onClickRepo() {
		Repository repo = mock(Repository.class);
		when(repo.getName()).thenReturn("foo-repo");

		presenter.onClickRepo(repo);

		verify(view).openUrl("https://github.com/explodes/foo-repo");
	}

}