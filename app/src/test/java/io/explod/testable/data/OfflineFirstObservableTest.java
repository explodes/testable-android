package io.explod.testable.data;

import android.support.annotation.NonNull;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import meta.BaseRoboTest;
import meta.TestModules;
import meta.rx.ImmediateSchedulerRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OfflineFirstObservableTest extends BaseRoboTest {

	@Rule public ImmediateSchedulerRule immediate = new ImmediateSchedulerRule();

	private static class TestSource {

		@NonNull
		private final String value;

		public boolean called = false;

		public TestSource(@NonNull String value) {
			this.value = value;
		}

		public Single<String> getSource() {
			return Single.fromCallable(() -> value).doOnEvent((x, e) -> called = true);
		}
	}

	@Test
	public void fromOfflineThenOnline_online() throws Exception {
		new TestModules().testInternetConnectivityService.setConnected(true);

		TestSource offline = new TestSource("offline");
		TestSource online = new TestSource("online");
		OfflineFirstObservable<String> observable = OfflineFirstObservable.from(offline.getSource(), online.getSource());
		List<String> results = new ArrayList<>(2);

		// verify observables were not automatically triggered
		assertFalse(offline.called);
		assertFalse(online.called);

		observable.fromOfflineThenOnline().subscribe(results::add);

		// verify callbacks
		assertTrue(offline.called);
		assertTrue(online.called);

		// verify order of callbacks
		assertEquals(2, results.size());
		assertEquals("offline", results.get(0));
		assertEquals("online", results.get(1));
	}

	@Test
	public void fromOfflineThenOnline_offline() throws Exception {
		new TestModules().testInternetConnectivityService.setConnected(false);

		TestSource offline = new TestSource("offline");
		TestSource online = new TestSource("online");
		OfflineFirstObservable<String> observable = OfflineFirstObservable.from(offline.getSource(), online.getSource());
		List<String> results = new ArrayList<>(2);

		// verify observables were not automatically triggered
		assertFalse(offline.called);
		assertFalse(online.called);

		observable.fromOfflineThenOnline().subscribe(results::add);

		// verify callbacks
		assertTrue(offline.called);
		assertFalse(online.called);

		// verify order of callbacks
		assertEquals(1, results.size());
		assertEquals("offline", results.get(0));
	}

	@Test
	public void fromOfflineOnly() throws Exception {
		TestSource offline = new TestSource("offline");
		TestSource online = new TestSource("online");
		OfflineFirstObservable<String> observable = OfflineFirstObservable.from(offline.getSource(), online.getSource());
		List<String> results = new ArrayList<>(2);

		// verify observables were not automatically triggered
		assertFalse(offline.called);
		assertFalse(online.called);

		observable.fromOfflineOnly().subscribe((Consumer<String>) results::add);

		// verify callbacks
		assertTrue(offline.called);
		assertFalse(online.called);

		// verify order of callbacks
		assertEquals(1, results.size());
		assertEquals("offline", results.get(0));
	}

	@Test
	public void fromOnlineOrOffline_online() throws Exception {
		new TestModules().testInternetConnectivityService.setConnected(true);

		TestSource offline = new TestSource("offline");
		TestSource online = new TestSource("online");
		OfflineFirstObservable<String> observable = OfflineFirstObservable.from(offline.getSource(), online.getSource());
		List<String> results = new ArrayList<>(2);

		// verify observables were not automatically triggered
		assertFalse(offline.called);
		assertFalse(online.called);

		observable.fromOnlineOrOffline().subscribe((Consumer<String>) results::add);

		// verify callbacks
		assertFalse(offline.called);
		assertTrue(online.called);

		// verify order of callbacks
		assertEquals(1, results.size());
		assertEquals("online", results.get(0));
	}

	@Test
	public void fromOnlineOrOffline_offline() throws Exception {
		new TestModules().testInternetConnectivityService.setConnected(false);

		TestSource offline = new TestSource("offline");
		TestSource online = new TestSource("online");
		OfflineFirstObservable<String> observable = OfflineFirstObservable.from(offline.getSource(), online.getSource());
		List<String> results = new ArrayList<>(2);

		// verify observables were not automatically triggered
		assertFalse(offline.called);
		assertFalse(online.called);

		observable.fromOnlineOrOffline().subscribe((Consumer<String>) results::add);

		// verify callbacks
		assertTrue(offline.called);
		assertFalse(online.called);

		// verify order of callbacks
		assertEquals(1, results.size());
		assertEquals("offline", results.get(0));
	}

}