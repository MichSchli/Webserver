package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.IResponse;
import ServerStack.Response.TextResponse;

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
