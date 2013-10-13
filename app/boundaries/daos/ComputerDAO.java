//THIS FILE HAS BEEN AUTO-GENERATED. DO NOT MODIFY.
package boundaries.daos;


import java.util.ArrayList;
import java.util.List;

import play.Logger;

import mef.entities.Computer;
import mef.gen.ComputerDAO_GEN;
import mef.presenters.MyPage;
import models.ComputerModel;

import com.avaje.ebean.Page;
public class ComputerDAO extends ComputerDAO_GEN
{

	@Override
	public Page<Computer> page(int page, int pageSize, String orderBy, String order, String filter)
	{
		Logger.info(String.format("P %d %d '%s'-'%s' '%s'", page, pageSize, orderBy, order, filter));

		List<ComputerModel> list = 
				ComputerModel.find.where()
				.ilike("name",  "%" + filter + "%" )
				.orderBy(orderBy + " " + order)
				.fetch("company").findList();

		List<Computer> L = createEntityFromModel(list);
		MyPage<Computer> resultPage = new MyPage<Computer>(L, pageSize, page, orderBy);
		return resultPage;
	}

}
