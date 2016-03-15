package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.TextResponse;

public interface IRequestHandler {

	public TextResponse Process(Request request);

	
}
