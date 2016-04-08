package Utilities.Configuration;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Utilities.Cast.CastException;
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

	public List<IConfiguration> readConfigurationFile(String filename) throws ConfigurationException, FileNotFoundException {
		List<List<String>> segments = _fileHandler.readSegments(filename);

		List<IConfiguration> configurations = new ArrayList<IConfiguration>();

		for (List<String> segment : segments) {
			configurations.add(readConfigurationSegment(segment));
		}

		return configurations;
	}

	private IConfiguration readConfigurationSegment(List<String> segment) throws ConfigurationException {
		String headerName = segment.remove(0);
		IConfiguration config = getConfigObject(headerName);

		Class<? extends IConfiguration> configClass = config.getClass();

		while (segment.size() > 0) {
			String string = segment.remove(0).trim();

			if (string.startsWith("[") || string.endsWith("]")) {
				throw new ConfigurationException("Wrongful configuration syntax");
			}

			String[] parts = string.split("\t");

			if (parts.length != 2) {
				throw new ConfigurationException("Wrongful configuration syntax");
			}

			try {
				Field field = configClass.getDeclaredField(parts[0]);
				Type type = field.getGenericType();

				if (type.getTypeName().equals(String.class.getTypeName())) {
					field.set(config, parts[1]);
				} else if (type.getTypeName().startsWith(List.class.getTypeName())) {
					if (!parts[1].equals("{")){
						throw new ConfigurationException("Wrongful cast in configuration for "+ configClass.getSimpleName() + ": " + parts[0]);
					} else{
						List<Object> listValue = readListValue(segment, field, type);
						field.set(config, listValue);
					}
				} else {
					field.set(config, _castHandler.cast(parts[1], type.getTypeName()));
				}

			} catch (NoSuchFieldException e) {
				throw new ConfigurationException(
						"Field not defined for " + configClass.getSimpleName() + ": " + parts[0]);
			} catch (SecurityException e) {
				throw new ConfigurationException(
						"Field not defined for " + configClass.getSimpleName() + ": " + parts[0]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (CastException e) {
				throw new ConfigurationException("Wrongful cast in configuration for "+ configClass.getSimpleName() + ": " + parts[0]);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return config;
	}

	private List<Object> readListValue(List<String> segment, Field field, Type listType) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, CastException {
		String currentElement = segment.remove(0).trim();
		Type elementType = ((ParameterizedType) listType).getActualTypeArguments()[0];

		List<Object> list = new ArrayList<Object>();
		while(!currentElement.equals("}")){
			if (elementType.getTypeName().equals(String.class.getTypeName())){
				list.add(currentElement);
			} else{
				list.add(_castHandler.cast(currentElement, elementType.getTypeName()));
			}
			currentElement = segment.remove(0).trim();
		}
		
		return list;
	}

	private IConfiguration getConfigObject(String headerName) throws ConfigurationException {
		if (!headerName.startsWith("[") || !headerName.endsWith("]")) {
			throw new ConfigurationException("Wrongful configuration syntax");
		}
		headerName = headerName.substring(1, headerName.length() - 1);

		IConfiguration config = _mapper.mapHeader(headerName);
		return config;
	}

}
