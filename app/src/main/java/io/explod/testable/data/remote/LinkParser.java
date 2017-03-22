package io.explod.testable.data.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkParser {

	private static final Pattern LINK_REGEX = Pattern.compile("<([^>]+)>; rel=\"(\\w+)\"");

	@Nullable
	static String parseLink(@Nullable String linkHeader, @NonNull String rel) {
		if (TextUtils.isEmpty(linkHeader)) return null;
		Matcher matcher = LINK_REGEX.matcher(linkHeader);
		while (matcher.find()) {
			String data = matcher.group(1);
			String relType = matcher.group(2);
			if (rel.equals(relType)) {
				return data;
			}

		}
		return null;
	}
}
