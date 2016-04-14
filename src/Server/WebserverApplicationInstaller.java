package Server;

import java.io.FileNotFoundException;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.parameters.ComponentParameter;

import Metadata.Server.IMetadataServer;
import Metadata.Server.MetadataServer;
import Services.UnknownRequestHandler;
import Services.Common.IRequestHandler;
import Services.Html.PageService;
import Services.ImageService.ImageService;
import Services.ImageService.ImageServiceConfiguration;
import Services.Logging.LoggingService;
import Services.MetadataService.MetadataService;
import Services.MetadataService.MetadataServiceConfiguration;
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
import Utilities.IO.JsonFileHandler;

public class WebserverApplicationInstaller {
	private DefaultPicoContainer _container;

	public WebserverApplicationInstaller(String configurationFile) throws FileNotFoundException, ConfigurationException {
		this._container = new DefaultPicoContainer();
		
		InstallUtilityComponents(_container);
		InstallConfigurations(_container, configurationFile);
		
		InstallMetadataServer(_container);
		
		InstallServiceStack(_container);
	}
	
	public IRequestHandler GetNewRequestHandler(){
		return (IRequestHandler) _container.getComponent(IRequestHandler.class);
	}
	
	private void InstallUtilityComponents(DefaultPicoContainer container){
		container.addComponent(CastHandler.class);
		container.addComponent(JavaFileHandler.class);
		container.addComponent(ExtendedFileHandler.class,ExtendedFileHandler.class, new ComponentParameter(JavaFileHandler.class));
		container.addComponent(IFileHandler.class, JsonFileHandler.class, new ComponentParameter(ExtendedFileHandler.class));
	}
	
	private void InstallConfigurations(DefaultPicoContainer container, String configurationFile) throws FileNotFoundException, ConfigurationException{
		container.addComponent(ConfigurationMapper.class);
		container.addComponent(ConfigurationReader.class);
		
		ConfigurationReader configReader = (ConfigurationReader) container.getComponent(ConfigurationReader.class);
		
		for (IConfiguration config : configReader.readConfigurationFile(configurationFile)) {
			container.addComponent(config);
		}
	}
	
	private void InstallServiceStack(DefaultPicoContainer container){
		container.addComponent(UnknownRequestHandler.class);

		container.addComponent(ImageService.class, ImageService.class, new ComponentParameter[] {
				new ComponentParameter(UnknownRequestHandler.class), 
				new ComponentParameter(ImageServiceConfiguration.class)}
		);
		
		container.addComponent(PageService.class, PageService.class, new ComponentParameter[] {
				new ComponentParameter(ImageService.class), 
				new ComponentParameter(IFileHandler.class)}
		);
		

		container.addComponent(MetadataService.class, MetadataService.class, new ComponentParameter[] {
				new ComponentParameter(PageService.class), 
				new ComponentParameter(MetadataServiceConfiguration.class),
				new ComponentParameter(IMetadataServer.class)}
		);
		
		container.addComponent(RedirectionService.class, RedirectionService.class, new ComponentParameter[] {
				new ComponentParameter(MetadataService.class), 
				new ComponentParameter(RedirectionServiceConfiguration.class)}
		);
		
		container.addComponent(IRequestHandler.class, LoggingService.class, new ComponentParameter(RedirectionService.class));
	}
	
	private void InstallMetadataServer(DefaultPicoContainer container){
		container.addComponent(IMetadataServer.class, MetadataServer.class);
	}
}
