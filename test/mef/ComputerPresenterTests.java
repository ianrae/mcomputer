package mef;

import static org.junit.Assert.*;

import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.mef.framework.commands.Command;
import org.mef.framework.commands.CreateCommand;
import org.mef.framework.commands.DeleteCommand;
import org.mef.framework.commands.EditCommand;
import org.mef.framework.commands.IndexCommand;
import org.mef.framework.commands.NewCommand;
import org.mef.framework.commands.ShowCommand;
import org.mef.framework.commands.UpdateCommand;
import org.mef.framework.presenters.Presenter;
import org.mef.framework.replies.Reply;
import org.mef.framework.sfx.SfxContext;

import mef.core.Initializer;
import mef.daos.IComputerDAO;
import mef.daos.mocks.MockComputerDAO;
import mef.entities.Computer;
import mef.presenters.ComputerPresenter;
import mef.presenters.MyPage;
import mef.presenters.commands.IndexComputerCommand;
import mef.presenters.replies.ComputerReply;

public class ComputerPresenterTests extends BasePresenterTest
{

	//HTTP  URL             ACTION  FRM    VIEW/REDIR     
	//GET 	users/    		index          INDEX
	//GET 	users/new 		new            NEW
	//POST  users/new   	create  form   r:index, NEW(if validation fails)
	//GET   users/:id/edit  edit           EDIT, NOTFOUND(if invalid id)
	//POST  users/:id/edit  update  form   r:index, NOTFOUND(if invalid id), EDIT(if validation fails)
	//POST 	users/:id/delete delete		   r:index, NOTFOUND(if invalid id)
	//GET   users/:id       show           SHOW,NOTFOUND

	//--- index ---
	@Test
	public void indexTest() 
	{
		ComputerReply reply = (ComputerReply) _presenter.process(new IndexComputerCommand());
		
		chkReplySucessful(reply, Reply.VIEW_INDEX, null);
		chkDalSize(0);
		chkReplyWithoutEntity(reply, true, 0);
	}

	@Test
	public void indexTestOne() 
	{
		Initializer.loadSeedData(_ctx);
		IndexComputerCommand cmd = new IndexComputerCommand();
		cmd.orderBy = null;
		ComputerReply reply = (ComputerReply) _presenter.process(cmd);
		
		chkReplySucessful(reply, Reply.VIEW_INDEX, null);
		
		List<Computer> L = reply.page.getList();
		assertEquals("MacBook Pro 15.4 inch", L.get(0).name);
		assertEquals(83, L.get(0).introduced.getYear());
		chkReplyWithoutEntity(reply, true, 4);
		
		log("page 2..");
		reply = (ComputerReply) _presenter.process(new IndexComputerCommand(4, 1, null, ""));
		
		chkReplySucessful(reply, Reply.VIEW_INDEX, null);
		
		L = reply.page.getList();
		assertEquals("CM-5", L.get(0).name);
		chkReplyWithoutEntity(reply, true, 4);
	}
	
	@Test
	public void indexTestOrderByName() 
	{
		Initializer.loadSeedData(_ctx);
		IndexComputerCommand cmd = new IndexComputerCommand();
		ComputerReply reply = (ComputerReply) _presenter.process(cmd);
		
		chkReplySucessful(reply, Reply.VIEW_INDEX, null);
		
		List<Computer> L = reply.page.getList();
		assertEquals("ACE", L.get(0).name);
		assertEquals(null, L.get(0).introduced);
		chkReplyWithoutEntity(reply, true, 4);
	}
	
	//--------- helper fns--------------
	protected void chkDalSize(int expected)
	{
		assertEquals(expected, _dao.size());
	}
	private void chkReplyWithEntity(ComputerReply reply, boolean listExists, int expected)
	{
		assertNotNull(reply._entity);
		if (listExists)
		{
			MyPage<Computer> page = reply.page;
			assertNotNull(page);
			assertEquals(expected, page.getList().size());
		}
		else
		{
			assertNull(reply.page);
		}
	}
	private void chkReplyWithoutEntity(ComputerReply reply, boolean listExists, int expected)
	{
		assertEquals(null, reply._entity);
		MyPage<Computer> page = reply.page;
		if (listExists)
		{
			assertNotNull(page);
			assertEquals(expected, page.getList().size());
		}
		else
		{
			assertNull(page);
		}
	}
	
	private MockComputerDAO _dao;
	private ComputerPresenter _presenter;
	@Before
	public void init()
	{
		super.init();
		_dao = getDAO();
		this._presenter = new ComputerPresenter(_ctx);
	}
	
	private MockComputerDAO getDAO()
	{
		MockComputerDAO dal = (MockComputerDAO) Initializer.getDAO(IComputerDAO.class); 
		return dal;
	}
	
	private Computer initComputer()
	{
		Computer t = new Computer();
		t.id = 0L; //dal will assign id
		t.name = "task1";
		assertEquals(0, _dao.size());
		return t;
	}
	
	private Computer initAndSaveComputer()
	{
		Computer t = initComputer();
		_dao.save(t);
		assertEquals(1, _dao.size());
		return t;
	}
	private Computer createComputer(String name)
	{
		Computer u = new Computer();
		u.name = name;
		return u;
	}
	
	
}
