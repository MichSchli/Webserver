package Services.Logging;

import Services.Common.IRequestHandler;
import Utilities.Request.Request;
import Utilities.Response.IResponse;
import Utilities.Response.TextResponse;

public class ServiceLogger implements IRequestHandler{

	private IRequestHandler next;
	public ServiceLogger(IRequestHandler next) {
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
