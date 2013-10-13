package mef.presenters;

import java.util.ArrayList;
import java.util.List;
import mef.entities.Computer;
import mef.daos.IComputerDAO;

import com.avaje.ebean.Page;

public class MyPage<T> implements Page<T>
{
	List<T> L;
	int pageSize;
	int pageNum;  //0-based
	String orderBy;

	public MyPage(List<T> L, int pageSize, int pageNum, String orderBy)
	{
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.orderBy = orderBy;
		this.L = L;
	}

	@Override
	public String getDisplayXtoYofZ(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getList() 
	{
		int start = (pageNum) * pageSize;
		int end = (start + pageSize <= L.size()) ? start + pageSize : L.size();

		if (start < 0 || start > (L.size() - 1) || end > L.size())
		{
			return new ArrayList<T>(); //empty
		}
		List<T> someL = L.subList(start, end);
		return someL;
	}

	@Override
	public int getPageIndex() 
	{
		return this.pageNum; //0-based
	}

	@Override
	public int getTotalPageCount() 
	{
		int n = L.size() / this.pageSize;
		int rem = L.size() % this.pageSize;

		return (rem > 0) ? n + 1 : n;
	}

	@Override
	public int getTotalRowCount() 
	{
		return L.size();
	}

	@Override
	public boolean hasNext() 
	{
		return (this.pageNum < (this.getTotalPageCount() - 1));
	}

	@Override
	public boolean hasPrev() 
	{
		return (this.pageNum > 0);
	}

	@Override
	public Page<T> next() 
	{
		Page<T> pg = new MyPage<T>(L, pageSize, pageNum + 1, orderBy);
		return pg;
	}

	@Override
	public Page<T> prev() 
	{
		Page<T> pg = new MyPage<T>(L, pageSize, pageNum - 1, orderBy);
		return pg;
	}

}
