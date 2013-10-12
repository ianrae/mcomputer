package controllers;

import java.util.*;

import org.mef.framework.commands.IndexCommand;
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
    
    public static Result index(int pageNum) 
    {
		ComputerBoundary boundary = ComputerBoundary.create();
		ComputerReply reply = boundary.process(new IndexComputerCommand(10, pageNum)); // orderBy, filter));
		return renderOrForward(boundary, reply);
    }
    
    private static Result renderOrForward(ComputerBoundary boundary, ComputerReply reply)
    {
		if (reply.failed())
		{
			return redirect(routes.ErrorC.logout());
		}
		
//		Form<UserModel> frm = null;
		switch(reply.getDestination())
		{
		case Reply.VIEW_INDEX:
			return ok(views.html.index.render(reply.page));    	


		default:
			return play.mvc.Results.redirect(routes.ErrorC.logout());	
    	}
	}

}
            
