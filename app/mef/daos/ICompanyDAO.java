//THIS FILE HAS BEEN AUTO-GENERATED. DO NOT MODIFY.

package mef.daos;

import mef.entities.*;
import java.util.List;
import org.mef.framework.binder.IFormBinder;
import org.mef.framework.dao.IDAO;
import mef.gen.*;
import java.util.Date;
import com.avaje.ebean.Page;
public interface ICompanyDAO  extends IDAO
{
	Company findById(long id);
	List<Company> all();
	void save(Company entity);        
	void update(Company entity);

    public Company find_by_name(String val);

}
