package Utilities.Url;

import java.util.Arrays;
import java.util.List;

import Utilities.Pattern;

public class UrlFactory {

	public static Url FromString(String stringUrl) {
		String[] parts = stringUrl.split("\\?");
		Pattern path = readPath(parts[0]);
		Url url = new Url(path);
		
		if (parts.length > 1){
			url.Queries.addAll(readQueries(parts[1]));
		}
		
		
		return url;
	}

	private static List<String> readQueries(String string) {
		return Arrays.asList(string.split("&"));
	}

	private static Pattern readPath(String stringUrl) {
		return new Pattern(stringUrl);
	}

}
