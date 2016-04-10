import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import Utilities.Cast.CastHandler;
import Utilities.Configuration.ConfigurationException;
import Utilities.Configuration.ConfigurationMapper;
import Utilities.Configuration.ConfigurationReader;
import Utilities.Configuration.IConfiguration;
import Utilities.IO.ExtendedFileHandler;
import Utilities.IO.JavaFileHandler;

public class Listener {
	
	public static void main(String[] args) throws IOException, ConfigurationException {
		ServerSocket welcomeSocket = new ServerSocket(9001);
		
		CastHandler castHandler = new CastHandler();
		ExtendedFileHandler fileHandler = new ExtendedFileHandler(new JavaFileHandler());
		ConfigurationMapper configMapper = new ConfigurationMapper();
		
		ConfigurationReader configReader = new ConfigurationReader(fileHandler, configMapper, castHandler);
		
		List<IConfiguration> configurations = configReader.readConfigurationFile(args[0]);

		System.out.println(configurations);
		
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			
			System.out.println("accepted");
			
			RequestProcessor requestProcessor = new RequestProcessor(connectionSocket, configurations);
			Thread thread = new Thread(requestProcessor);
			thread.start();
			
			System.out.println("started thread, listening again...");
		}
	}
}
