package io.explod.testable.util.mvp.base;

import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import meta.BaseRoboTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BaseRxPresenterTest extends BaseRoboTest {

	private interface RxPresenterView extends BaseView {
	}

	private static class RxPresenterImpl extends BaseRxPresenter<RxPresenterView> {

	}

	RxPresenterImpl presenter;

	@Before
	public void createPresenter() {
		presenter = new RxPresenterImpl();
	}

	private static void dispose(@Nullable Disposable disposable) {
		if (disposable != null && !disposable.isDisposed()) disposable.dispose();
	}

	@Test
	public void bind() throws Exception {
		Disposable a = null;
		try {
			BehaviorSubject<Boolean> observable = BehaviorSubject.create();
			a = presenter.bind(observable).subscribe();
			assertFalse(a.isDisposed());

		} finally {
			dispose(a);
		}
	}

	@Test
	public void bind_with_key() throws Exception {
		Disposable a = null;
		try {
			BehaviorSubject<Boolean> observable = BehaviorSubject.create();
			a = presenter.bind("key", observable).subscribe();
			assertFalse(a.isDisposed());

		} finally {
			dispose(a);
		}
	}

	@Test
	public void unbindAll() throws Exception {
		Disposable a = null;
		Disposable b = null;
		Disposable c = null;
		try {
			BehaviorSubject<Boolean> observable = BehaviorSubject.create();
			a = presenter.bind(observable).subscribe();
			b = presenter.bind("key1", observable).subscribe();
			c = presenter.bind("key2", observable).subscribe();
			assertFalse(a.isDisposed());
			assertFalse(b.isDisposed());
			assertFalse(c.isDisposed());

			presenter.unbindAll();

			assertTrue(a.isDisposed());
			assertTrue(b.isDisposed());
			assertTrue(c.isDisposed());
		} finally {
			dispose(a);
			dispose(b);
			dispose(c);
		}
	}

	@Test
	public void unbind() throws Exception {
		Disposable a = null;
		Disposable b = null;
		Disposable c = null;
		try {
			BehaviorSubject<Boolean> observable = BehaviorSubject.create();
			a = presenter.bind(observable).subscribe();
			b = presenter.bind("key1", observable).subscribe();
			c = presenter.bind("key2", observable).subscribe();
			assertFalse(a.isDisposed());
			assertFalse(b.isDisposed());
			assertFalse(c.isDisposed());

			presenter.unbind("key1");

			assertFalse(a.isDisposed());
			assertTrue(b.isDisposed());
			assertFalse(c.isDisposed());
		} finally {
			dispose(a);
			dispose(b);
			dispose(c);
		}
	}

}