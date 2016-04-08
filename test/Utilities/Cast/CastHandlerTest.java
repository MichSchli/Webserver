package Utilities.Cast;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;


public class CastHandlerTest {

	@Test
	public void Error_ImpossibleCast(){
		CastHandler caster = new CastHandler();
		try {
			caster.cast("holyshitfuckabc", "fisk");
			fail("No exception thrown");
		} catch (CastException e) {
			Assert.assertEquals("Cannot cast from String to fisk", e.getMessage());
		}
	}
	
	@Test
	public void Cast_StringToInt() throws CastException{
		CastHandler caster = new CastHandler();
		int cast = (int) caster.cast("27", int.class.getTypeName());
		Assert.assertEquals(27, cast);
	}
	
	@Test
	public void Error_StringToInt(){
		CastHandler caster = new CastHandler();
		int cast;
		try {
			cast = (int) caster.cast("abc27", int.class.getTypeName());
			fail("No exception thrown");
		} catch (CastException e) {
			Assert.assertEquals("Cannot cast String \"abc27\" to int", e.getMessage());
		}
	}
}
