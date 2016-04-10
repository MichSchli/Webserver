package Services.Common;

import java.util.List;

import Services.UnknownRequestHandler;
import Services.Html.PageService;
import Services.Images.ImageService;
import Services.Logging.ServiceLogger;
import Services.Redirection.RedirectionService;
import Services.Redirection.RedirectionServiceConfiguration;
import Utilities.Configuration.IConfiguration;
import Utilities.IO.ExtendedFileHandler;
import Utilities.IO.JavaFileHandler;
import Utilities.Request.Request;
import Utilities.Response.IResponse;

public class ServerStackTop {

	private IRequestHandler top;

	public ServerStackTop(List<IConfiguration> configurations) {
		IRequestHandler l1 = new UnknownRequestHandler();
		IRequestHandler l2 = new ImageService(l1);
		IRequestHandler l3 = new PageService(l2, new ExtendedFileHandler(new JavaFileHandler()));
		
		for (IConfiguration config : configurations) {
			if (config.getClass().getTypeName().endsWith("RedirectionServiceConfiguration")){
				IRequestHandler l4 = new RedirectionService(l3, (RedirectionServiceConfiguration) config);
				this.top = new ServiceLogger(l4);
			}
		}
		
		
	}
	
	public IResponse Process(Request request) {
		return top.Process(request);
	}

}
