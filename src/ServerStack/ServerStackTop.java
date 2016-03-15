package ServerStack;

import ServerStack.Response.IResponse;

public class ServerStackTop {

	private IRequestHandler top;

	public ServerStackTop() {
		IRequestHandler l1 = new UnknownRequestHandler();
		this.top = l1;
	}
	
	public IResponse Process(Request request) {
		return top.Process(request);
	}

}
