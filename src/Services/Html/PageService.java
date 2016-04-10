package Services.Html;

import Services.Common.BaseRequestHandler;
import Services.Common.IRequestHandler;
import Utilities.Pattern;
import Utilities.IO.IFileHandler;
import Utilities.Request.Request;
import Utilities.Response.HtmlResponse;
import Utilities.Response.IResponse;
import Utilities.Response.ImageResponse;

public class PageService extends BaseRequestHandler {

	private Pattern pattern;
	private IFileHandler _fileHandler;

	public PageService(IRequestHandler next, IFileHandler fileHandler) {
		super(next);
		pattern = new Pattern("pages/*");
		this._fileHandler = fileHandler;
	}

	@Override
	public boolean Accept(Request request) {
		return pattern.Match(request.Address);
	}

	@Override
	public IResponse GetResponse(Request request) {
		return new HtmlResponse("/"+request.Address.next+".html", _fileHandler);
	}

}
