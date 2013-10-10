package controllers;

import java.util.*;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;

import views.html.*;

import models.*;

/**
 * Manage a database of computers
 */
public class Application extends Controller 
{
    
    public static Result index() {
  	
		return ok(views.html.index.render(""));    	
  	
  }
    

}
            
