package ServerStack;

import java.io.IOException;

import ServerStack.Request.Request;
import ServerStack.Response.IResponse;
import ServerStack.Response.ImageResponse;
import Utilities.Pattern.Pattern;

public class ImageService extends BaseRequestHandler{

	private Pattern pattern;

	public ImageService(IRequestHandler next) {
		super(next);
		pattern = new Pattern("image/*".split("/"), 0);
	}

	@Override
	public boolean Accept(Request request) {
		return pattern.Match(request.Address);
	}

	@Override
	public IResponse GetResponse(Request request) {
		try {
			return new ImageResponse("/"+request.Address.next+".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
