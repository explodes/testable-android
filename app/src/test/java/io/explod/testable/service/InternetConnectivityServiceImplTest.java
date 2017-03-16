package io.explod.testable.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import meta.BaseRoboTest;

import static org.junit.Assert.assertTrue;

public class InternetConnectivityServiceImplTest extends BaseRoboTest {

	InternetConnectivityServiceImpl service;

	@Before
	public void createService() {
		service = new InternetConnectivityServiceImpl();
	}

	@After
	public void unregisterService() {
		service.unregisterReceiver();
	}

	@Test
	public void isConnected() throws Exception {
		assertTrue(service.isConnected());
	}

	@Test
	public void connectionObservable() throws Exception {
		boolean connected = service.connectionObservable().blockingFirst();

		assertTrue(connected);
	}

}