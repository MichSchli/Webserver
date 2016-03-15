package ServerStack;

import ServerStack.Response.TextResponse;

public class UnknownRequestHandler implements IRequestHandler {

	@Override
	public TextResponse Process(Request request) {
		return ResponseFactory.Error("200", "The requested service does not exist.");
	}

}
