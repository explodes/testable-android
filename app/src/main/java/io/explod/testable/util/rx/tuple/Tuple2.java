package io.explod.testable.util.rx.tuple;

import android.support.annotation.NonNull;

public class Tuple2<A, B> {


	@NonNull
	public static <A, B> Tuple2<A, B> of(@NonNull A a, @NonNull B b) {
		return new Tuple2<>(a, b);
	}

	@NonNull
	private final A a;

	@NonNull
	private final B b;

	private Tuple2(@NonNull A a, @NonNull B b) {
		this.a = a;
		this.b = b;
	}

	@NonNull
	public A getA() {
		return a;
	}

	@NonNull
	public B getB() {
		return b;
	}
}
