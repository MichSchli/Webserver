package Services.Logging;

import Services.Common.IRequestHandler;
import Utilities.Request.Request;
import Utilities.Response.IResponse;

public class LoggingService implements IRequestHandler{

	private IRequestHandler next;
	public LoggingService(IRequestHandler next) {
		this.next = next;
	}
	
	@Override
	public IResponse Process(Request request) {
		System.out.println(request);
		
		IResponse response = next.Process(request);

		System.out.println(response);
		return response;
	}

}
