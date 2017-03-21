package io.explod.testable.ui.fragment.home;

import android.app.Activity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import meta.BaseRoboTest;
import meta.FragUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HomeFragmentTest extends BaseRoboTest {

	@Test
	public void onViewCreated() {
		HomeFragment frag = FragUtils.resumeFragment(HomeFragment.newInstance());

		assertNotNull(frag.mRecyclerView);
		assertNotNull(frag.mRepoAdapter);
	}

	@Test
	public void setUser() {
		User user = mock(User.class);
		when(user.getName()).thenReturn("TEST NAME");
		HomeFragment frag = FragUtils.resumeFragment(HomeFragment.newInstance());
		Activity activity = frag.getActivity();

		frag.setUser(user);

		assertEquals("TEST NAME", activity.getTitle().toString());
	}

	@Test
	public void setRepositories() {
		Repository repo = mock(Repository.class);
		when(repo.getName()).thenReturn("NAME");
		when(repo.getDescription()).thenReturn("DESCRIPTION");
		List<Repository> repos = new ArrayList<>(3);
		repos.add(repo);
		repos.add(repo);
		repos.add(repo);

		HomeFragment frag = FragUtils.resumeFragment(HomeFragment.newInstance());
		frag.setRepositories(repos);

		assertEquals(3, frag.mRepoAdapter.getItemCount());
	}

	@Test
	public void openUrl() {

	}

}