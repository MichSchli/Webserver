package ServerStack.Request;

import Utilities.Pattern.Pattern;

public class Request {

	public String Method;
	public Pattern Address;

	@Override
	public String toString() {
		return "Request:\nMethod:\t"+Method+"\n"+Address+"\n---------------------------";
	}
}
