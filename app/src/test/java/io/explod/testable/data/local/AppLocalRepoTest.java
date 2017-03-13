package io.explod.testable.data.local;

import android.annotation.SuppressLint;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import meta.BaseRoboTest;
import meta.rx.ImmediateSchedulerRule;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@SuppressLint("DefaultLocale")
@RunWith(RobolectricTestRunner.class)
public class AppLocalRepoTest extends BaseRoboTest {

	@Rule
	public ImmediateSchedulerRule rule = new ImmediateSchedulerRule();

	AppLocalRepo repo;

	@Before
	public void createRepo() {
		repo = new AppLocalRepo();
	}

	@Test
	public void insertAndGetGithubRepositories() throws Exception {
		AtomicInteger count = new AtomicInteger(0);

		repo
			.insertUser("explodes")
			.flatMap(userId -> repo.insertRepository(userId, "test-repo1-" + userId).map(x -> userId))
			.flatMap(userId -> repo.insertRepository(userId, "test-repo2-" + userId).map(x -> userId))
			.flatMap(userId -> repo.getRepositories())
			.subscribe(repo -> count.addAndGet(1), e -> fail(e.toString()));

		assertEquals(2, count.get());
	}

	@Test
	public void insertAndGetGithubUsers() throws Exception {
		AtomicInteger count = new AtomicInteger(0);

		Observable.merge(repo.insertUser("abc"), repo.insertUser("123")).subscribe(x -> {
		}, e -> fail(e.toString()));
		// subscribe in separate observable to make this test slightly different than the
		// equivalent github repository test
		repo.getUsers().subscribe(repo -> count.addAndGet(1), e -> fail(e.toString()));

		assertEquals(2, count.get());
	}

}