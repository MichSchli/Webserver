package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.HtmlResponse;
import ServerStack.Response.IResponse;
import ServerStack.Response.ImageResponse;
import Utilities.Pattern;

public class PageService extends BaseRequestHandler {

	private Pattern pattern;

	public PageService(IRequestHandler next) {
		super(next);
		pattern = new Pattern("pages/*");
	}

	@Override
	public boolean Accept(Request request) {
		return pattern.Match(request.Address);
	}

	@Override
	public IResponse GetResponse(Request request) {
		return new HtmlResponse("/"+request.Address.next+".html");
	}

}
