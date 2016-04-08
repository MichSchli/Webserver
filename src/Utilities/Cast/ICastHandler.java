package Utilities.Cast;

import java.lang.reflect.Type;

public interface ICastHandler{

	Object cast(String string, Type elementType) throws CastException;

}
