import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ComponentParameter;

import Services.UnknownRequestHandler;
import Services.Common.IRequestHandler;
import Services.Html.PageService;
import Services.ImageService.ImageService;
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

public class Listener {
	
	public static void main(String[] args) throws IOException, ConfigurationException {
		ServerSocket welcomeSocket = new ServerSocket(9001);
		
		/*
		CastHandler castHandler = new CastHandler();
		ExtendedFileHandler fileHandler = new ExtendedFileHandler(new JavaFileHandler());
		ConfigurationMapper configMapper = new ConfigurationMapper();
		
		ConfigurationReader configReader = new ConfigurationReader(fileHandler, configMapper, castHandler);
		*/
		
		ConfigurationReader configReader = GetConfigurationReader();
		List<IConfiguration> configurations = configReader.readConfigurationFile(args[0]);

		System.out.println(configurations);
		
		IRequestHandler requestHandler = GetProcessorStack(configurations);
		
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			
			System.out.println("accepted");
			
			RequestProcessor requestProcessor = new RequestProcessor(connectionSocket, configurations, requestHandler);
			Thread thread = new Thread(requestProcessor);
			thread.start();
			
			System.out.println("started thread, listening again...");
		}
	}
	
	public static ConfigurationReader GetConfigurationReader(){
		MutablePicoContainer pico = new DefaultPicoContainer();
		pico.addComponent(CastHandler.class);
		pico.addComponent(JavaFileHandler.class);
		pico.addComponent(IFileHandler.class, ExtendedFileHandler.class);
		pico.addComponent(ConfigurationMapper.class);
		pico.addComponent(ConfigurationReader.class);
		
		return (ConfigurationReader) pico.getComponent(ConfigurationReader.class);
	}
	
	public static IRequestHandler GetProcessorStack(List<IConfiguration> configurations){
		MutablePicoContainer pico = new DefaultPicoContainer();
		pico.addComponent(UnknownRequestHandler.class);
		pico.addComponent(ImageService.class, ImageService.class, new ComponentParameter(UnknownRequestHandler.class));
		pico.addComponent(JavaFileHandler.class);
		pico.addComponent(IFileHandler.class, ExtendedFileHandler.class);
		pico.addComponent(PageService.class, PageService.class, new ComponentParameter[] {
				new ComponentParameter(ImageService.class), 
				new ComponentParameter(IFileHandler.class)});

		for (IConfiguration config : configurations) {
			if (config.getClass().getTypeName().endsWith("RedirectionServiceConfiguration")){
				pico.addComponent((RedirectionServiceConfiguration) config);
			}
		}
		
		pico.addComponent(RedirectionService.class, RedirectionService.class, new ComponentParameter[] {
				new ComponentParameter(PageService.class), 
				new ComponentParameter(RedirectionServiceConfiguration.class)});
		
		pico.addComponent(LoggingService.class, LoggingService.class, new ComponentParameter(RedirectionService.class));
		

		return (IRequestHandler) pico.getComponent(LoggingService.class);
	}
}
