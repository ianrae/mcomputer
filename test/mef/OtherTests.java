package mef;

import static org.junit.Assert.*;

import mef.core.Initializer;

import org.junit.Test;

public class OtherTests 
{

	@Test
	public void test() 
	{
		Initializer.createContext(true); //don't call init in unit tests
		assertNotNull(Initializer.theCtx);
		
		Initializer.loadSeedData(Initializer.theCtx);
	}

}
