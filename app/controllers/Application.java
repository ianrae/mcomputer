package controllers;

import java.util.*;

import org.mef.framework.commands.CreateCommand;
import org.mef.framework.commands.IndexCommand;
import org.mef.framework.commands.NewCommand;
import org.mef.framework.replies.Reply;

import boundaries.ComputerBoundary;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;

import views.html.*;

import mef.presenters.commands.IndexComputerCommand;
import mef.presenters.replies.ComputerReply;
import models.*;

/**
 * Manage a database of computers
 */
public class Application extends Controller 
{
    //p:Int ?= 1, s ?= "name", o ?= "asc", f ?= ""
    public static Result index(int pageNum, String sortBy, String orderBy, String filter) 
    {
    	Logger.info("PAGE: " + pageNum);
		ComputerBoundary boundary = ComputerBoundary.create();
		ComputerReply reply = boundary.process(new IndexComputerCommand(10, pageNum, sortBy,orderBy, filter));
		return renderOrForward(boundary, reply, sortBy, orderBy, filter);
    }
    
    public static Result edit(Long id) 
    {
        return ok("asdf");
    }   
    public static Result update(Long id) 
    {
        return ok("asdf");
    }   
    public static Result delete(Long id) 
    {
        return ok("asdf");
    }   
    public static Result save() 
    {
		ComputerBoundary boundary = ComputerBoundary.create();
		ComputerReply reply = boundary.addFormAndProcess(new CreateCommand());
		return renderOrForward(boundary, reply, "","","");
    }   
    
    public static Result create() 
    {
		ComputerBoundary boundary = ComputerBoundary.create();
		ComputerReply reply = boundary.process(new NewCommand());
		return renderOrForward(boundary, reply, "", "", "");
    }
    
    private static Result renderOrForward(ComputerBoundary boundary, ComputerReply reply,  String sortBy, String orderBy, String filter)
    {
		if (reply.failed())
		{
			return redirect(routes.ErrorC.logout());
		}
		
		Form<ComputerModel> frm = null;
		switch(reply.getDestination())
		{
		case Reply.VIEW_INDEX:
			return ok(views.html.index.render(reply.page, sortBy, orderBy, filter));    	
		case Reply.VIEW_NEW:
			frm = boundary.makeForm(reply); 
			return ok(views.html.createForm.render(frm, reply._options));


		default:
			return play.mvc.Results.redirect(routes.ErrorC.logout());	
    	}
	}

}
            
