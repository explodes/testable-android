package io.explod.testable.module.modules;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import dagger.Module;
import dagger.Provides;

@Module
public class GsonModule {

	private static final String OUTPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private static final SimpleDateFormat OUTPUT_DATE_FORMATTER;

	static {
		OUTPUT_DATE_FORMATTER = new SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.US);
		OUTPUT_DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private static final SimpleDateFormat[] DATE_FORMATS = new SimpleDateFormat[]{
		new SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.US),
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US),
		new SimpleDateFormat("yyyy-MM-dd", Locale.US),
	};

	@Provides
	@NonNull
	Gson providesGson() {
		return build(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
	}

	@VisibleForTesting
	Gson build(@NonNull FieldNamingPolicy fieldNamingPolicy) {
		return new GsonBuilder()
			.registerTypeAdapter(Date.class, new DateFormatter())
			.setFieldNamingPolicy(fieldNamingPolicy)
			.create();
	}

	private static class DateFormatter implements JsonDeserializer<Date>, JsonSerializer<Date> {

		@Override
		public Date deserialize(@NonNull JsonElement jsonElement, @NonNull Type typeOfDest, @NonNull JsonDeserializationContext context) throws JsonParseException {
			String value = jsonElement.getAsString().replaceAll("Z$", "+0000");
			for (SimpleDateFormat format : DATE_FORMATS) {
				Date parsed;
				try {
					parsed = format.parse(value);
				} catch (Exception e) {
					// keep moving forward
					continue;
				}
				return parsed;
			}
			throw new JsonParseException("Could not parse date: \"" + jsonElement.getAsString() + "\".");
		}

		@Nullable
		@Override
		public JsonElement serialize(@Nullable Date src, @NonNull Type typeOfSrc, @NonNull JsonSerializationContext context) {
			if (src == null) {
				return null;
			}
			String formatted = OUTPUT_DATE_FORMATTER.format(src);
			return new JsonPrimitive(formatted);
		}
	}
}
