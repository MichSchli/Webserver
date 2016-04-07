package Services;

import Utilities.Request.Request;
import Utilities.Response.IResponse;
import Utilities.Response.TextResponse;

public interface IRequestHandler {

	public IResponse Process(Request request);

	
}
