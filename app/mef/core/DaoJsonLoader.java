package mef.core;

import org.codehaus.jackson.JsonNode;

import mef.entities.Company;
import mef.entities.Computer;
import mef.gen.DaoJsonLoader_GEN;

public class DaoJsonLoader extends DaoJsonLoader_GEN
{
	@Override
	public Computer readComputer(JsonNode node)
	{
		Computer obj = super.readComputer(node);
		JsonNode jj = node.get("company");
		if (jj != null)
		{
			jj = jj.get("id");
			if (jj != null)
			{
				obj.company = new Company();
				obj.company.id = jj.asLong();
			}
			
		}
		jj = node.get("introduced");
		obj.introduced = this.readDate(jj, "yyyy-MM-dd");
		jj = node.get("discontinued");
		obj.discontinued = this.readDate(jj, "yyyy-MM-dd");
		return obj;
	}
}