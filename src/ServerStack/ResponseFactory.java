package ServerStack;

import ServerStack.Response.TextResponse;

public class ResponseFactory {

	public static TextResponse Error(String code, String message) {
		return new TextResponse(code, message);
	}

}
