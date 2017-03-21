package meta.rx;

import android.support.annotation.NonNull;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static org.junit.Assert.fail;

public class FailOnErrorSingleObserver<T> implements SingleObserver<T> {

	@NonNull
	public static <T> FailOnErrorSingleObserver<T> consumeSingleOrFail() {
		return new FailOnErrorSingleObserver<>();
	}

	@Override
	public void onSubscribe(Disposable d) {
	}

	@Override
	public void onSuccess(T t) {
	}

	@Override
	public void onError(Throwable e) {
		fail(e.toString());
	}

}
