package mef.daos.mocks;

import java.util.ArrayList;
import java.util.List;

import mef.entities.Computer;
import mef.gen.MockComputerDAO_GEN;

public class MockComputerDAO extends MockComputerDAO_GEN
{

	public List<Computer> all_order_by(String fieldName, String orderBy)
	{
		ArrayList<Computer> tmpL = new ArrayList<Computer>();
		tmpL.addAll(_L);
		List<Computer> list1 = tmpL;
		_entityDB.orderBy(list1, fieldName, orderBy, String.class);
		return list1;
	}
}
