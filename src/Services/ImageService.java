package Services;

import java.io.IOException;

import Utilities.Pattern;
import Utilities.Request.Request;
import Utilities.Response.IResponse;
import Utilities.Response.ImageResponse;

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
