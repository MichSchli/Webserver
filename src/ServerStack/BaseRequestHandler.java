package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.IResponse;

public abstract class BaseRequestHandler implements IRequestHandler{

	private IRequestHandler next;

	public abstract boolean Accept(Request request);
	public abstract IResponse GetResponse(Request request);
	
	public BaseRequestHandler(IRequestHandler next) {
		this.next = next;
	}
	
	public IResponse Process(Request request){
		if (Accept(request)){
			return GetResponse(request);
		}else{
			return next.Process(request);
		}
	}
}
