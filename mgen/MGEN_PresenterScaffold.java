


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mef.tools.mgen.codegen.PresenterScaffoldCodeGenerator;


//********************** CAREFUL!!! ****************************

public class MGEN_PresenterScaffold extends BaseTest
{
	@Test
	public void testEntity() throws Exception
	{
		createContext();
		PresenterScaffoldCodeGenerator gen = new PresenterScaffoldCodeGenerator(_ctx);
		
		gen.disableFileIO = true;  //***** WATCH OUT!11 ****
		
		String appDir = this.getCurrentDir("");
		String stDir = this.getCurrentDir("..\\dalgen\\code\\mettle\\conf\\mgen\\resources\\presenter");
log(appDir);
log(stDir);
		
		int n = gen.init(appDir, stDir);
		assertEquals(2, n);

		boolean b = false;
//		boolean b = gen.generate(0);
		b = gen.generate("Company");
		b = gen.generate("Computer");
		if (b)
		{}
	}

}
