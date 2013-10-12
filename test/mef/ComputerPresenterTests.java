package mef;

import static org.junit.Assert.*;


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
		ComputerReply reply = (ComputerReply) _presenter.process(new IndexCommand());
		
		chkReplySucessful(reply, Reply.VIEW_INDEX, null);
		chkDalSize(0);
		chkReplyWithoutEntity(reply, true, 0);
	}

	@Test
	public void indexTestOne() 
	{
		Initializer.loadSeedData(_ctx);
		ComputerReply reply = (ComputerReply) _presenter.process(new IndexCommand());
		
		chkReplySucessful(reply, Reply.VIEW_INDEX, null);
		assertEquals("MacBook Pro 15.4 inch", _dao.all().get(0).name);
		chkReplyWithoutEntity(reply, true, 571);
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
			assertNotNull(reply._allL);
			assertEquals(expected, reply._allL.size());
		}
		else
		{
			assertNull(reply._allL);
		}
	}
	private void chkReplyWithoutEntity(ComputerReply reply, boolean listExists, int expected)
	{
		assertEquals(null, reply._entity);
		if (listExists)
		{
			assertNotNull(reply._allL);
			assertEquals(expected, reply._allL.size());
		}
		else
		{
			assertNull(reply._allL);
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
