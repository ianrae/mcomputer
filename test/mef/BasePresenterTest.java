package mef;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import mef.entities.Computer;

import org.mef.framework.commands.Command;
import org.mef.framework.replies.Reply;
import org.mef.framework.test.helpers.MockFormBinder;

public class BasePresenterTest extends BaseTest
{
	protected void chkReplySucessful(Reply reply, int view, String flash)
	{
		assertNotNull(reply);
		assertEquals(false, reply.failed()); //should go to error page. something bad happened
		assertEquals(view, reply.getDestination());
		assertEquals(flash, reply.getFlash());
	}

	protected Command createWithBinder(Command cmd, Computer t, boolean bindingIsValid)
	{
		MockFormBinder binder = new MockFormBinder(t);
		cmd.setFormBinder(binder);
		binder.isValid = bindingIsValid;
		return cmd;
	}
	
}
