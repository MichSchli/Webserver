package Utilities.Configuration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import Utilities.Cast.ICastHandler;
import Utilities.IO.IFileHandler;

public class ConfigurationReader {

	private IFileHandler _fileHandler;
	private IConfigurationMapper _mapper;
	private ICastHandler _castHandler;

	
	public ConfigurationReader(IFileHandler fileHandler, IConfigurationMapper mapper, ICastHandler castHandler) {
		this._fileHandler = fileHandler;
		this._mapper = mapper;
		this._castHandler = castHandler;
	}

	public List<IConfiguration> readConfigurationFile(String filename){
		List<List<String>> segments = _fileHandler.readSegments(filename);
		
		List<IConfiguration> configurations = new ArrayList<IConfiguration>();
		
		for (List<String> segment : segments) {
			configurations.add(readConfigurationSegment(segment));
		}
		
		return configurations;
	}

	private IConfiguration readConfigurationSegment(List<String> segment) {
		String headerName = segment.remove(0);
		IConfiguration config = getConfigObject(headerName);
		Class configClass = config.getClass();
		
		for (String string : segment) {
			String[] parts = string.split("\t");
			
			try {
				Field field = configClass.getDeclaredField(parts[0]);
				Class<?> type = field.getType();
				
				if (type.isAssignableFrom(String.class)) {
					field.set(config, parts[1]);
				} else{
					field.set(config, _castHandler.cast(parts[1], type));
				}
				
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return config;
	}

	private IConfiguration getConfigObject(String headerName) {
		headerName = headerName.substring(1, headerName.length()-1);
		
		IConfiguration config = _mapper.mapHeader(headerName);
		return config;
	}
	
}
