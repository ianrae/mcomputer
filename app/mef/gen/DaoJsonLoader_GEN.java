//THIS FILE HAS BEEN AUTO-GENERATED. DO NOT MODIFY.

package mef.gen;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.JsonNode;

import play.data.format.Formats;
import play.data.format.Formats.DateFormatter;
import mef.entities.*;
import java.util.Date;



public class DaoJsonLoader_GEN
{


	public Company readCompany(JsonNode node)
	{
		Company obj = new Company();
		JsonNode jj = node.get("id");
		obj.id = jj.asLong();

				jj = node.get("name");
				obj.name = jj.getTextValue();



		return obj;
	}
	public List<Company> loadCompanys(JsonNode rootNode) 
	{
		List<Company> phoneL = new ArrayList<Company>();

    	JsonNode msgNode = rootNode.path("Company");
		Iterator<JsonNode> ite = msgNode.getElements();

		int i = 0;
		while (ite.hasNext()) {
			JsonNode temp = ite.next();
			Company ph = readCompany(temp);

			phoneL.add(ph);
			i++;
		}    	

		return phoneL;
	}
	protected Company findCompanyWithId(long id, List<Company> phoneL) 
	{
		for (Company ph : phoneL)
		{
			if (ph.id == id)
			{
				return ph;
			}
		}
		return null;
	}
	public Computer readComputer(JsonNode node)
	{
		Computer obj = new Computer();
		JsonNode jj = node.get("id");
		obj.id = jj.asLong();

				jj = node.get("name");
				obj.name = jj.getTextValue();

				jj = node.get("introduced");
				obj.introduced = readDate(jj, "yyyy-MM-dd");

				jj = node.get("discontinued");

				jj = node.get("company");



		return obj;
	}
	private Date readDate(JsonNode jj, String pattern)
	{
		if (jj == null)
		{
			return null;
		}
		
		Formats.DateFormatter fmt = new DateFormatter(pattern);
		Locale loc = Locale.getDefault();

		Date dt = null;
		try {
			dt = fmt.parse(jj.getTextValue(), loc);
		} catch (ParseException e) 
		{
			//log error!!
			e.printStackTrace();
		}
		return dt;
	}
	public List<Computer> loadComputers(JsonNode rootNode) 
	{
		List<Computer> phoneL = new ArrayList<Computer>();

    	JsonNode msgNode = rootNode.path("Computer");
		Iterator<JsonNode> ite = msgNode.getElements();

		int i = 0;
		while (ite.hasNext()) {
			JsonNode temp = ite.next();
			Computer ph = readComputer(temp);

			phoneL.add(ph);
			i++;
		}    	

		return phoneL;
	}
	protected Computer findComputerWithId(long id, List<Computer> phoneL) 
	{
		for (Computer ph : phoneL)
		{
			if (ph.id == id)
			{
				return ph;
			}
		}
		return null;
	}
}
