package mef.presenters.commands;

import org.mef.framework.commands.IndexCommand;

public class IndexComputerCommand extends IndexCommand 
{
	public int pageSize;
	public int pageNum; //0-based
	public String orderBy;
	public String filter;
	
	public IndexComputerCommand()
	{
		pageSize = 4;
		pageNum = 0;
		orderBy = "name";
		filter = "";
	}

	public IndexComputerCommand(int pageSize, int pageNum)
	{
		this(pageSize, pageNum, "name", "");
	}
	public IndexComputerCommand(int pageSize, int pageNum, String orderBy, String filter)
	{
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.orderBy = orderBy;
		this.filter = filter;
	}
}
