package Services.ImageService;

import java.io.IOException;

import Services.Common.BaseRequestHandler;
import Services.Common.IRequestHandler;
import Utilities.Pattern;
import Utilities.Request.Request;
import Utilities.Response.IResponse;
import Utilities.Response.ImageResponse;

public class ImageService extends BaseRequestHandler{

	private Pattern pattern;
	private ImageServiceConfiguration _configuration;

	public ImageService(IRequestHandler next, ImageServiceConfiguration configuration) {
		super(next);
		_configuration = configuration;
		
		String[] targetAddress = {_configuration.ServiceName, "*"};
		pattern = new Pattern(targetAddress, 0);
	}

	@Override
	public boolean Accept(Request request) {
		return pattern.Match(request.Address);
	}

	@Override
	public IResponse GetResponse(Request request) {
		System.out.println("image");
		try {
			return new ImageResponse("/"+request.Address.next+".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
