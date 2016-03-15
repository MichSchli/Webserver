package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.IResponse;
import ServerStack.Response.TextResponse;

public interface IRequestHandler {

	public IResponse Process(Request request);

	
}
