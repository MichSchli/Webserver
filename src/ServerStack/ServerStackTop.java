package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.IResponse;

public class ServerStackTop {

	private IRequestHandler top;

	public ServerStackTop() {
		IRequestHandler l1 = new UnknownRequestHandler();
		IRequestHandler l2 = new ServiceLogger(l1);
		this.top = l2;
	}
	
	public IResponse Process(Request request) {
		return top.Process(request);
	}

}
