package Utilities.Request;

import Utilities.Pattern;

public class Request {

	public RequestMethodType Method;
	public Pattern Address;

	@Override
	public String toString() {
		return "Request:\nMethod:\t"+Method+"\n"+Address+"\n---------------------------";
	}
}
