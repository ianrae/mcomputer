//THIS FILE HAS BEEN AUTO-GENERATED. DO NOT MODIFY.
package boundaries.daos;


import java.util.ArrayList;
import java.util.List;

import mef.entities.Computer;
import mef.gen.ComputerDAO_GEN;
import mef.presenters.MyPage;
import models.ComputerModel;

import com.avaje.ebean.Page;
public class ComputerDAO extends ComputerDAO_GEN
{

	@Override
	public Page<Computer> page(int page, int pageSize, String orderBy, String filter)
	{
		String sortBy = "name";
		String order = "asc";

		List<ComputerModel> list = 
				ComputerModel.find.where()
				.ilike("name",  filter )
				.orderBy(sortBy + " " + order)
				.fetch("company").findList();

		List<Computer> L = createEntityFromModel(list);
		MyPage<Computer> resultPage = new MyPage<Computer>(this, pageSize, page, orderBy);
		resultPage.forceList(L);
		return resultPage;
	}

}
