package io.explod.testable.module.modules;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import meta.BaseRoboTest;

import static org.junit.Assert.assertEquals;

public class GsonModuleTest extends BaseRoboTest {

	Gson gson;

	@Before
	public void createGson() {
		gson = new GsonModule().build(FieldNamingPolicy.IDENTITY);
	}

	@NonNull
	private Date timestamp(int year, int month, int date, int hourOfDay, int minute, int second, int millis, @NonNull TimeZone timeZone) {
		Calendar c = new GregorianCalendar(timeZone);
		c.set(year, month - 1, date, hourOfDay, minute, second);
		c.set(Calendar.MILLISECOND, millis);
		return c.getTime();
	}

	@Test
	public void parseDate_std_format() throws Exception {
		TypeAdapter<Date> adapter = gson.getAdapter(Date.class);

		// yyyy-MM-dd'T'HH:mm:ss.SSSZ
		Date parsed = adapter.fromJson("\"2017-03-15T20:49:05.333Z\"");
		assertEquals(timestamp(2017, 3, 15, 20, 49, 5, 333, TimeZone.getTimeZone("UTC")), parsed);
	}

	@Test
	public void parseDate_std_format_no_millis() throws Exception {
		TypeAdapter<Date> adapter = gson.getAdapter(Date.class);

		// yyyy-MM-dd'T'HH:mm:ss
		Date parsed = adapter.fromJson("\"2017-03-15T20:49:05Z\"");
		assertEquals(timestamp(2017, 3, 15, 20, 49, 5, 0, TimeZone.getTimeZone("UTC")), parsed);
	}

	@Test
	public void parseDate_std_format_explicity_timezone() throws Exception {
		TypeAdapter<Date> adapter = gson.getAdapter(Date.class);

		// yyyy-MM-dd'T'HH:mm:ss.SSS+ZZZZ
		Date parsed = adapter.fromJson("\"2017-03-15T20:49:05.333-0600\"");
		assertEquals(timestamp(2017, 3, 16, 2, 49, 5, 333, TimeZone.getTimeZone("UTC")), parsed);
	}

	@Test
	public void parseDate_date_only() throws Exception {
		TypeAdapter<Date> adapter = gson.getAdapter(Date.class);

		// yyyy-MM-dd
		Date parsed = adapter.fromJson("\"2017-03-15\"");
		assertEquals(timestamp(2017, 3, 15, 0, 0, 0, 0, TimeZone.getDefault()), parsed);
	}

}