//THIS FILE HAS BEEN AUTO-GENERATED. DO NOT MODIFY.

package mef.presenters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mef.framework.Logger;
import org.mef.framework.binder.IFormBinder;
import org.mef.framework.commands.CreateCommand;
import org.mef.framework.commands.DeleteCommand;
import org.mef.framework.commands.EditCommand;
import org.mef.framework.commands.IndexCommand;
import org.mef.framework.commands.Command;
import org.mef.framework.commands.NewCommand;
import org.mef.framework.commands.ShowCommand;
import org.mef.framework.commands.UpdateCommand;
import org.mef.framework.presenters.Presenter;
import org.mef.framework.replies.Reply;
import org.mef.framework.sfx.SfxBaseObj;
import org.mef.framework.sfx.SfxContext;

import mef.daos.ICompanyDAO;
import mef.daos.IComputerDAO;
import mef.entities.Company;
import mef.entities.Computer;
import mef.presenters.commands.IndexComputerCommand;
import mef.presenters.replies.ComputerReply;


public class ComputerPresenter extends Presenter
{
	private IComputerDAO _dao;
	private ComputerReply _reply;
	private ICompanyDAO _companyDao;

	public ComputerPresenter(SfxContext ctx)
	{
		super(ctx); 
		_dao = (IComputerDAO) getInstance(IComputerDAO.class);
		_companyDao = (ICompanyDAO) getInstance(ICompanyDAO.class);
	}
	@Override
	protected ComputerReply createReply()
	{
		_reply = new ComputerReply();
		return _reply;
	}

	public ComputerReply onIndexComputerCommand(IndexComputerCommand cmd)
	{
		ComputerReply reply = createReply(); 
		reply.page = _dao.page(cmd.pageNum, cmd.pageSize, cmd.orderBy, cmd.order, cmd.filter);
		reply.setDestination(Reply.VIEW_INDEX);
		return reply;
	}

	public ComputerReply onNewCommand(NewCommand cmd)
	{
		ComputerReply reply = createReply();
		reply.setDestination(Reply.VIEW_NEW);
		
		reply._entity = new Computer();
		//default vals
		reply._entity.name = "defaultname";
		reply._entity.company = this._companyDao.findById(1);
		addOptions(reply);
		return reply; //don't add list		
	}
	
	private void addOptions(ComputerReply reply) 
	{
		LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
		
		List<Company> L = _companyDao.all(); //!! add sorting later
		for(Company ph : L)
		{
			options.put(ph.id.toString(), ph.name);
		}
		reply._options = options;
	}		

	public ComputerReply onCreateCommand(CreateCommand cmd)
	{
		ComputerReply reply = createReply();
		reply.setDestination(Reply.VIEW_NEW);
		
		IFormBinder binder = cmd.getFormBinder();
		if (! binder.bind())
		{
			reply.setFlashFail("binding failed!");
			Logger.info("BINDING failed");
			reply._entity = (Computer) binder.getObject();
			addOptions(reply);
			return reply;
		}
		else
		{
			Computer entity = (Computer) binder.getObject();
			if (entity == null)
			{
				reply.setFailed(true);
			}
			else
			{
				_dao.save(entity);
				Logger.info("saved new");
				reply.setFlashSuccess("created computer " + entity.name);
				reply.setDestination(Reply.FORWARD_INDEX);
			}
			return reply;
		}
	}

	public ComputerReply onEditCommand(EditCommand cmd)
	{
		ComputerReply reply = createReply();
		reply.setDestination(Reply.VIEW_EDIT);
		
		Computer user = _dao.findById(cmd.id);
		if (user == null)
		{
			reply.setDestination(Reply.FORWARD_NOT_FOUND);
			return reply;
		}
		else
		{
			reply._entity = user;
			addOptions(reply);
			return reply;
		}
	}
	public ComputerReply onUpdateCommand(UpdateCommand cmd)
	{
		ComputerReply reply = createReply();
		reply.setDestination(Reply.VIEW_EDIT);
		return reply;
	}


	public ComputerReply onDeleteCommand(DeleteCommand cmd)
	{
		ComputerReply reply = createReply();
		reply.setDestination(Reply.FORWARD_INDEX);
		return reply;
	}

	public ComputerReply onShowCommand(ShowCommand cmd)
	{
		ComputerReply reply = createReply();
		reply.setDestination(Reply.VIEW_SHOW);
		return reply;
	}











}
