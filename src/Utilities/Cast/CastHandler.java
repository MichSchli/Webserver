package Utilities.Cast;

import java.lang.reflect.Type;

public class CastHandler implements ICastHandler{

	@Override
	public Object cast(String string, String type) throws CastException {
		switch (type) {
		case "int":
			try {
				return Integer.parseInt(string);
			} catch (Exception e) {
				throw new CastException("Cannot cast String \""+string+"\" to "+type);
			}
		default:
			throw new CastException("Cannot cast from String to "+type);
		}
	}

}
