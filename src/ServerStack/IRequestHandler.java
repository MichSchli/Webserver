package ServerStack;

import ServerStack.Response.TextResponse;

public interface IRequestHandler {

	public TextResponse Process(Request request);

	
}
