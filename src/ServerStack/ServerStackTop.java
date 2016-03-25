package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.IResponse;

public class ServerStackTop {

	private IRequestHandler top;

	public ServerStackTop() {
		IRequestHandler l1 = new UnknownRequestHandler();
		IRequestHandler l2 = new ImageService(l1);
		IRequestHandler l3 = new PageService(l2);
		
		IRequestHandler l4 = new RedirectService(l3);
		
		this.top = new ServiceLogger(l4);
	}
	
	public IResponse Process(Request request) {
		return top.Process(request);
	}

}
