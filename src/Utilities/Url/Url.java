package Utilities.Url;

import java.util.ArrayList;

import Utilities.Pattern;

public class Url {

	public Pattern Path;
	public ArrayList<String> Queries;
	
	public Url(Pattern path) {
		Path = path;
		Queries = new ArrayList<String>();
	}

}
