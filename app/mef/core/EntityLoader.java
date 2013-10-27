package mef.core;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import mef.daos.ICompanyDAO;
import mef.daos.IComputerDAO;
import mef.entities.Company;
import mef.entities.Computer;
import mef.gen.EntityLoaderSaver_GEN;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.mef.framework.entities.Entity;
import org.mef.framework.sfx.SfxBaseObj;
import org.mef.framework.sfx.SfxContext;

public class EntityLoader extends SfxBaseObj
{
	private ICompanyDAO companyDal;
	private IComputerDAO computerDal;
	
	public EntityLoader(SfxContext ctx)
	{
		super(ctx);
		companyDal = (ICompanyDAO) Initializer.getDAO(ICompanyDAO.class); 
		computerDal = (IComputerDAO) Initializer.getDAO(IComputerDAO.class); 
	}
	
	public void loadAll(String json) throws Exception
	{
    	DaoJsonLoader loader = new DaoJsonLoader();
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode rootNode = mapper.readTree(json);

    	//entities can have logical ids in the json, so that one entity can refer to another
    	//When entities stored in DB they will get a real id
    	//Keep a map to track logical-id to real-id.
    	//Logical id formatted as classname+id, eg "Company.20"
    	HashMap<String, Long> map = new HashMap<String, Long>();
    	
    	List<Company> phoneL = loader.loadCompanys(rootNode);
    	saveCompanies(phoneL, map);
    	
    	log("map:");
    	for(String key : map.keySet())
    	{
    		Long val = map.get(key);
    		log(String.format("%s -> %d", key, val));
    	}
    	
    	List<Computer> computerL = loader.loadComputers(rootNode);
    	saveComputers(computerL, map);
 	}


	private void saveCompanies(List<Company> phoneL, HashMap<String, Long> map) 
	{
    	for(Company ph : phoneL)
    	{
    		String key = makeKey(ph, ph.id);
    		Company existing = companyDal.find_by_name(ph.name); //use seedWith field
    		long id = EntityLoaderSaver_GEN.saveOrUpdate(ph, existing, companyDal);
    		map.put(key, id);    	}
	}
	
	private void saveComputers(List<Computer> computerL, HashMap<String, Long> map) 
	{
    	for(Computer computer : computerL)
    	{
    		if (computer.company != null)
    		{
    			long existingId = findIdInMap(computer.company, computer.company.id, map);
				if (existingId != 0L)
				{
		    		Company existing = companyDal.findById(existingId);
					computer.company = existing;
				}
    		}    		
    		
    		String key = makeKey(computer, computer.id);
    		Computer existing = computerDal.find_by_name(computer.name); //use seedWith field
    		long id = EntityLoaderSaver_GEN.saveOrUpdate(computer, existing, computerDal);
    		map.put(key, id);
    	}
    	
    	
    	
	}
	private String makeKey(Entity entity, Long id)
	{
		String s = entity.getClass().getSimpleName();
		s = String.format("%s.%d", s, id);
		return s;
	}

	private long findIdInMap(Entity entity, Long id, HashMap<String, Long> map) 
	{
		String key = makeKey(entity, id);
		Long physicalId = map.get(key);
		if (physicalId != null && physicalId.longValue() != 0L)
		{
			return physicalId.longValue();
		}
		else
		{
			log(String.format("ERR: can't find id: %s", key));
			return 0;
		}
	}	
}
