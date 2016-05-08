package Services.Common;

import Utilities.Request.Request;
import Utilities.Response.IResponse;

public interface IRequestHandler {

	public IResponse Process(Request request);

	
}
