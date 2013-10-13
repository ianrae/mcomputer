package mef.presenters.commands;

import org.mef.framework.commands.IndexCommand;

public class IndexComputerCommand extends IndexCommand 
{
	public int pageSize;
	public int pageNum; //0-based
	public String orderBy;
	public String order;
	public String filter;
	
	public IndexComputerCommand()
	{
		pageSize = 4;
		pageNum = 0;
		orderBy = "name";
		order = "asc";
		filter = "";
	}

	public IndexComputerCommand(int pageSize, int pageNum)
	{
		this(pageSize, pageNum, "name", "asc", "");
	}
	public IndexComputerCommand(int pageSize, int pageNum, String orderBy, String order, String filter)
	{
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.orderBy = orderBy;
		this.order = order;
		this.filter = filter;
	}
}
