//THIS FILE HAS BEEN AUTO-GENERATED. DO NOT MODIFY.

package mef.gen;

import java.util.ArrayList;
import java.util.List;
import org.mef.framework.dao.IDAO;
import mef.daos.*;
import mef.daos.mocks.*;
import boundaries.daos.*;
import org.mef.framework.sfx.SfxContext;
import java.util.Date;

public class AllKnownDAOs_GEN  
{
public List<IDAO> registerDAOs(SfxContext ctx, boolean createMocks)
{
	ArrayList<IDAO> L = new ArrayList<IDAO>();
    if (createMocks)
{
	ICompanyDAO dal = new MockCompanyDAO();
	ctx.getServiceLocator().registerSingleton(ICompanyDAO.class, dal);
	L.add(dal);
}
else
{
	ICompanyDAO dal = new CompanyDAO();
	ctx.getServiceLocator().registerSingleton(ICompanyDAO.class, dal);
	L.add(dal);
}	
	return L;
}
}
