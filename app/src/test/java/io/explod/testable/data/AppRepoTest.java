package io.explod.testable.data;

import android.support.v4.util.Pair;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.explod.testable.data.local.contract.RepositoryContract;
import io.explod.testable.data.local.contract.UserContract;
import io.explod.testable.data.local.model.Repository;
import io.explod.testable.data.local.model.User;
import meta.BaseRoboTest;
import meta.TestModules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AppRepoTest extends BaseRoboTest {

	TestModules modules;
	AppRepo repo;

	@Before
	public void createModules() {
		modules = new TestModules();
	}

	@Before
	public void createRepo() {
		repo = new AppRepo();
	}

	@Test
	public void getRepositories_online_saves_repos() throws Exception {
		modules.testInternetConnectivityService.setConnected(true);

		repo.getRepositories("mock-user").fromOnlineOrOffline().blockingGet();

		assertTrue(repo.mAppDatabase.users().exists(UserContract.Columns.USERNAME + " = ?", "mock-user").blockingGet());
		assertTrue(repo.mAppDatabase.repositories().exists(RepositoryContract.Columns.NAME + " = ?", "mock-user-a").blockingGet());
		assertTrue(repo.mAppDatabase.repositories().exists(RepositoryContract.Columns.NAME + " = ?", "mock-user-b").blockingGet());
		assertTrue(repo.mAppDatabase.repositories().exists(RepositoryContract.Columns.NAME + " = ?", "mock-user-c").blockingGet());
	}

	@Test
	public void getRepositories_online_gets_results() throws Exception {
		modules.testInternetConnectivityService.setConnected(true);

		Pair<User, List<Repository>> results = repo.getRepositories("mock-user").fromOnlineOrOffline().blockingGet();

		assertNotNull(results.first);
		assertEquals("mock-user", results.first.getName());

		assertNotNull(results.second);
		assertEquals(3, results.second.size());

		assertEquals("mock-user-a", results.second.get(0).getName());
		assertEquals(results.first.getId(), results.second.get(0).getUserId());

		assertEquals("mock-user-b", results.second.get(1).getName());
		assertEquals(results.first.getId(), results.second.get(1).getUserId());

		assertEquals("mock-user-c", results.second.get(2).getName());
		assertEquals(results.first.getId(), results.second.get(2).getUserId());
	}

}