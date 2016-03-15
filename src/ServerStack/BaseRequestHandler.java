package ServerStack;

import ServerStack.Request.Request;
import ServerStack.Response.TextResponse;

public abstract class BaseRequestHandler implements IRequestHandler{

	private IRequestHandler next;

	public abstract boolean Accept(Request request);
	public abstract TextResponse GetResponse(Request request);
	
	public BaseRequestHandler(IRequestHandler next) {
		this.next = next;
	}
	
	public TextResponse Process(Request request){
		if (Accept(request)){
			return GetResponse(request);
		}else{
			return next.Process(request);
		}
	}
}
