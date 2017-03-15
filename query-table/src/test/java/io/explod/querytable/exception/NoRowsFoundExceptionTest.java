package io.explod.querytable.exception;

import org.junit.Test;

import io.explod.querytable.WhereClause;

import static junit.framework.Assert.assertNotNull;

public class NoRowsFoundExceptionTest {

	@Test
	public void init() {
		WhereClause where = new WhereClause("abc", "1", "2", "3");

		NoRowsFoundException ex = new NoRowsFoundException("table", where);
		assertNotNull(ex);
	}

	@Test
	public void init_null_values() {
		NoRowsFoundException ex = new NoRowsFoundException("table", null);
		assertNotNull(ex);
	}

}