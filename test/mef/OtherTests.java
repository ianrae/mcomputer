package mef;

import static org.junit.Assert.*;

import mef.core.Initializer;

import org.junit.Test;

public class OtherTests 
{

	@Test
	public void test() 
	{
		Initializer.init();
		assertNotNull(Initializer.theCtx);
	}

}
