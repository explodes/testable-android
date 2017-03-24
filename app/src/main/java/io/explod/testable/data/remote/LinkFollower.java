package io.explod.testable.data.remote;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;

import static io.explod.testable.data.remote.LinkParser.parseLink;
import static io.explod.testable.module.ObjectGraph.getInjector;

public class LinkFollower<ResponseType, Collection> {

	@NonNull
	public static <ResponseType, Collection> Single<Collection> followNext(@NonNull Single<Response<ResponseType>> call, @NonNull TypeToken<ResponseType> typeToken, @NonNull Collection initialValue, @NonNull BiConsumer<? super Collection, ? super ResponseType> collector) {
		return follow(call, REL_NEXT, typeToken, initialValue, collector);
	}

	@NonNull
	public static <ResponseType, Collection> Single<Collection> follow(@NonNull Single<Response<ResponseType>> call, @NonNull String rel, @NonNull TypeToken<ResponseType> typeToken, @NonNull Collection initialValue, @NonNull BiConsumer<? super Collection, ? super ResponseType> collector) {
		return call.map(response -> new LinkFollower<>(rel, typeToken, initialValue, collector).follow(response));
	}

	private static final String REL_NEXT = "next";

	private static final String HEADER_LINK = "link";

	@NonNull
	private final Gson mGson;

	@NonNull
	private final OkHttpClient mOkHttpClient;

	@NonNull
	private final String mRel;

	@NonNull
	private final TypeToken<ResponseType> mTypeToken;

	@NonNull
	private final Collection mCollection;

	@NonNull
	private final BiConsumer<? super Collection, ? super ResponseType> mCollector;

	private LinkFollower(@NonNull String rel, @NonNull TypeToken<ResponseType> typeToken, @NonNull Collection initialValue, @NonNull BiConsumer<? super Collection, ? super ResponseType> collector) {
		mRel = rel;
		mTypeToken = typeToken;
		mCollection = initialValue;
		mCollector = collector;

		Injects injects = new Injects();
		mGson = injects.gson;
		mOkHttpClient = injects.okHttpClient;
	}

	@NonNull
	private Collection follow(@NonNull Response<ResponseType> response) throws Exception {
		mCollector.accept(mCollection, response.body());
		followWithResponse(response.raw());
		return mCollection;
	}

	private void followWithResponse(@NonNull okhttp3.Response response) throws Exception {
		String link = parseLink(response.header(HEADER_LINK), mRel);
		if (!TextUtils.isEmpty(link)) {
			Request request = new Request.Builder().get().url(link).build();
			response = mOkHttpClient.newCall(request).execute();
			ResponseType responseResult = mGson.getAdapter(mTypeToken).fromJson(response.body().charStream());
			mCollector.accept(mCollection, responseResult);
			followWithResponse(response);
		}
	}

	/**
	 * @hide Visible for injection
	 */
	public static class Injects {

		@Inject
		Gson gson;

		@Inject
		OkHttpClient okHttpClient;

		public Injects() {
			getInjector().inject(this);
		}

	}
}
