package Services;

import Services.Common.IRequestHandler;
import Utilities.Request.Request;
import Utilities.Response.ResponseFactory;
import Utilities.Response.TextResponse;

public class UnknownRequestHandler implements IRequestHandler {

	@Override
	public TextResponse Process(Request request) {
		return ResponseFactory.Error("404", "The requested service ("+request.Method+'@'+request.Address+") does not exist.");
	}

}
