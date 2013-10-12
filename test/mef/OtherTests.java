package mef;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Locale;

import mef.core.Initializer;

import org.junit.Test;

import play.data.format.Formats;
import play.data.format.Formats.DateFormatter;
import play.data.format.Formatters;


public class OtherTests 
{

	@Test
	public void test() 
	{
		Initializer.createContext(true); //don't call init in unit tests
		assertNotNull(Initializer.theCtx);
		
		Initializer.loadSeedData(Initializer.theCtx);
	}
	
	
	@Test
	public void testDates() throws Exception
	{
		Locale loc = Locale.getDefault();
		Formats.DateFormatter fmt = new DateFormatter("yyyy-MM-dd");
		
		Date dt = fmt.parse("1980-05-01", loc);
		assertEquals(80, dt.getYear());
		assertEquals(4, dt.getMonth()); //0-based month
		assertEquals(1, dt.getDate());
	}

}
