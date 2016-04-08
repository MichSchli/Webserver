package Utilities.Cast;

import java.lang.reflect.Type;

public interface ICastHandler{

	Object cast(String string, String type) throws CastException;

}
