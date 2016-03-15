package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.IResponse;

public class ServerStackTop {

	private IRequestHandler top;

	public ServerStackTop() {
		IRequestHandler l1 = new UnknownRequestHandler();
		IRequestHandler l2 = new ImageService(l1);
		
		this.top = new ServiceLogger(l2);
	}
	
	public IResponse Process(Request request) {
		return top.Process(request);
	}

}
