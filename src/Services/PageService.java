package Services;

import Utilities.Pattern;
import Utilities.Request.Request;
import Utilities.Response.HtmlResponse;
import Utilities.Response.IResponse;
import Utilities.Response.ImageResponse;

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
