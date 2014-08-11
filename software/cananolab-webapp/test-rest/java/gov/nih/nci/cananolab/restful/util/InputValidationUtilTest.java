package gov.nih.nci.cananolab.restful.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class InputValidationUtilTest {

	@Test
	public void testIsAlphabetic() {
		boolean is = InputValidationUtil.isAlphabetic("sfiealaea");
		assertTrue(is);
	}
	
	@Test
	public void testIsAlphabeticNullInput() {
		boolean is = InputValidationUtil.isAlphabetic(null);
		assertFalse(is);
	}

	@Test
	public void testIsAlphabeticNotMatch() {
		boolean is = InputValidationUtil.isAlphabetic("daasl4");
		assertFalse(is);
	}
	
	@Test
	public void testIsDOI() {
		boolean is = InputValidationUtil.doi(null);
		assertFalse(is);
	}
	
	@Test
	public void testIsZipValid() {
		String zip = "22222-22229";
		boolean is = InputValidationUtil.isZipValid(zip);
		assertTrue(is);
	}
 }
