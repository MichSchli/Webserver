package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.ResponseFactory;
import ServerStack.Response.TextResponse;

public class UnknownRequestHandler implements IRequestHandler {

	@Override
	public TextResponse Process(Request request) {
		return ResponseFactory.Error("200", "The requested service ("+request.Method+'@'+request.Address+") does not exist.");
	}

}
