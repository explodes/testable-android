package io.explod.testable.util.rx;

import com.fernandocejas.arrow.optional.Optional;

import org.junit.Test;

import static io.explod.testable.util.rx.OptionalUtils.optional;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OptionalUtilsTest {

	@Test
	public void optional_with_null() throws Exception {
		Optional<Boolean> o = optional(null);

		assertFalse(o.isPresent());
	}

	@Test
	public void optional_with_value() throws Exception {
		Optional<Boolean> o = optional(true);

		assertTrue(o.isPresent());
	}

}