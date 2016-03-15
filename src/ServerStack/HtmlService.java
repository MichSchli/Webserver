package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.HtmlResponse;
import ServerStack.Response.IResponse;
import Utilities.Pattern.Pattern;

public class HtmlService extends BaseRequestHandler {

	private Pattern pattern;

	public HtmlService(IRequestHandler next) {
		super(next);
		pattern = new Pattern("home");
	}

	@Override
	public boolean Accept(Request request) {
		return pattern.Match(request.Address);
	}

	@Override
	public IResponse GetResponse(Request request) {
		return new HtmlResponse();
	}

}
