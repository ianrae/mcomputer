package mef.daos.mocks;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Page;

import mef.entities.Computer;
import mef.gen.MockComputerDAO_GEN;
import mef.presenters.MyPage;

public class MockComputerDAO extends MockComputerDAO_GEN
{
	@Override
	public Page<Computer> page(int page, int pageSize,String orderBy, String order, String filter)
	{
		ArrayList<Computer> tmpL = new ArrayList<Computer>();
		tmpL.addAll(_L);
		List<Computer> list1 = tmpL;
		if (orderBy != null)
		{
			_entityDB.orderBy(list1, orderBy, order, String.class);
		}

		return new MyPage<Computer>(list1, pageSize, page, orderBy);
	}
}
