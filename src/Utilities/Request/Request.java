package Utilities.Request;

import java.util.ArrayList;

import Utilities.Pattern;

public class Request {

	public RequestMethodType Method;
	public Pattern Address;
	public ArrayList<String> Queries;

	@Override
	public String toString() {
		return "Request:\nMethod:\t"+Method+"\n"+Address+"\n---------------------------";
	}
}
