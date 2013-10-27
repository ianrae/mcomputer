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

import com.avaje.ebean.Page;

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
		Computer c = L.get(0);
		assertEquals("MacBook Pro 15.4 inch", c.name);
		assertEquals(83, c.introduced.getYear());
		chkReplyWithoutEntity(reply, true, 4);
		
		assertEquals("Apple Inc.", c.company.name);
		
		log("page 2..");
		reply = (ComputerReply) _presenter.process(new IndexComputerCommand(4, 1, null, "asc", ""));
		
		chkReplySucessful(reply, Reply.VIEW_INDEX, null);
		
		L = reply.page.getList();
		c = L.get(0);
		assertEquals("CM-5", c.name);
		chkReplyWithoutEntity(reply, true, 4);
		
		assertEquals("5 to 8 of 571", reply.page.getDisplayXtoYofZ(" to "," of "));
		assertEquals("Thinking Machines", c.company.name);
	}
	
	@Test
	public void indexTestOrderByName() 
	{
		Initializer.loadSeedData(_ctx);
		IndexComputerCommand cmd = new IndexComputerCommand();
		cmd.orderBy = "name";
		ComputerReply reply = (ComputerReply) _presenter.process(cmd);
		
		chkReplySucessful(reply, Reply.VIEW_INDEX, null);
		
		List<Computer> L = reply.page.getList();
		assertEquals("ACE", L.get(0).name);
		assertEquals(null, L.get(0).introduced);
		chkReplyWithoutEntity(reply, true, 4);
	}
	
	@Test
	public void testNew() 
	{
		Initializer.loadSeedData(_ctx);
		NewCommand cmd = new NewCommand();
		ComputerReply reply = (ComputerReply) _presenter.process(cmd);
		
		chkReplySucessful(reply, Reply.VIEW_NEW, null);
		chkReplyWithEntity(reply, false, 0);
		assertNotNull(reply._entity.name);
		assertNotNull(reply._entity.company);
		assertNotNull(reply._options);
	}
	
	
	//--- create ---
	@Test
	public void testCreate() 
	{
		Computer t = initComputer();
		int n = _dao.all().size();
		Command cmd = createWithBinder(new CreateCommand(), t, true);
		
		ComputerReply reply = (ComputerReply) _presenter.process(cmd);
		
		chkReplySucessful(reply, Reply.FORWARD_INDEX, "created computer task1");
		chkReplyWithoutEntity(reply, false, n + 1);
		t = _dao.findById(1);
		assertEquals(new Long(1L), t.id);
	}
	@Test
	public void testCreate_ValFail() 
	{
		Computer t = initComputer();
		Command cmd = createWithBinder(new CreateCommand(), t, false);
		
		ComputerReply reply = (ComputerReply) _presenter.process(cmd);
		
		chkReplySucessful(reply, Reply.VIEW_NEW, "binding failed!");
		chkDalSize(0);
		chkReplyWithEntity(reply, false, 0);
		assertNotNull(reply._options);
	}	
	
	
	//--- edit ---
	@Test
	public void testEdit() 
	{
		Computer t = initAndSaveComputer();
		int n = _dao.all().size();
		ComputerReply reply = (ComputerReply) _presenter.process(new EditCommand(t.id));
		
		chkReplySucessful(reply, Reply.VIEW_EDIT, null);
		chkDalSize(1);
		chkReplyWithEntity(reply, false, 0);
		assertNotNull(reply._options);
		
	}
	@Test
	public void testEdit_NotFound() 
	{
		Computer t = initAndSaveComputer();
		int n = _dao.all().size();
		ComputerReply reply = (ComputerReply) _presenter.process(new EditCommand(9999L));
		
		chkReplySucessful(reply, Reply.FORWARD_NOT_FOUND, null);
		chkDalSize(1);
		chkReplyWithoutEntity(reply, false, 0);
		assertNull(reply._options);
		
	}
	
	//--- update ---
	@Test
	public void testUpdate() 
	{
		Computer t = initAndSaveComputer();
		t.name = "task2"; //simulate user edit
		Command cmd = createWithBinder(new UpdateCommand(t.id), t, true);
		
		ComputerReply reply = (ComputerReply) _presenter.process(cmd);
		
		chkReplySucessful(reply, Reply.FORWARD_INDEX, null);
		chkDalSize(1);
		chkReplyWithoutEntity(reply, false, 1);
		
		Computer t2 = _dao.findById(t.id);
		assertEquals("task2", t2.name);
	}

	//--- delete ---
	@Test
	public void testDelete() 
	{
		Computer t = initAndSaveComputer();
		ComputerReply reply = (ComputerReply) _presenter.process( new DeleteCommand(t.id));
		
		chkReplySucessful(reply, Reply.FORWARD_INDEX, null);
		chkDalSize(0);
		chkReplyWithoutEntity(reply, false, 0);
	}
	
	@Test
	public void testBadDeleteUser() 
	{
		Computer t = initAndSaveComputer();
		ComputerReply reply = (ComputerReply) _presenter.process(new DeleteCommand(99L)); //not exist
		
		chkReplySucessful(reply, Reply.FORWARD_NOT_FOUND, "could not find computer");
		chkDalSize(1);
		chkReplyWithoutEntity(reply, false, 0);
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
			Page<Computer> page = reply.page;
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
		Page<Computer> page = reply.page;
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
