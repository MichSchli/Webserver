import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ComponentParameter;

import Server.WebserverApplicationInstaller;
import Server.RequestProcessor;
import Services.UnknownRequestHandler;
import Services.Common.IRequestHandler;
import Services.Html.PageService;
import Services.ImageService.ImageService;
import Services.ImageService.ImageServiceConfiguration;
import Services.Logging.LoggingService;
import Services.Redirection.RedirectionService;
import Services.Redirection.RedirectionServiceConfiguration;
import Utilities.Cast.CastHandler;
import Utilities.Configuration.ConfigurationException;
import Utilities.Configuration.ConfigurationMapper;
import Utilities.Configuration.ConfigurationReader;
import Utilities.Configuration.IConfiguration;
import Utilities.IO.ExtendedFileHandler;
import Utilities.IO.IFileHandler;
import Utilities.IO.JavaFileHandler;
import api.Client;

public class Listener {
	
	public static void main(String[] args) throws IOException, ConfigurationException {
		ServerSocket welcomeSocket = new ServerSocket(9001);
		
		WebserverApplicationInstaller installer = new WebserverApplicationInstaller(args[0]);
						
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			
			System.out.println("accepted");
			
			RequestProcessor requestProcessor = new RequestProcessor(connectionSocket, installer.GetNewRequestHandler());
			Thread thread = new Thread(requestProcessor);
			thread.start();
			
			System.out.println("started thread, listening again...");
		}
	}
}
