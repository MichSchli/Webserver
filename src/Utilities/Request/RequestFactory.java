package Utilities.Request;

import java.io.BufferedReader;
import java.io.IOException;

import Utilities.Url.Url;
import Utilities.Url.UrlFactory;

public class RequestFactory {

	public static Request ReadFromBufferedReader(BufferedReader reader) throws IOException {
	
		Request request = new Request();
		
		//Read the request line:
		String line = reader.readLine();
		System.out.println(line);
		String[] l = line.split(" ");
		
		//For now always get:
		request.Method = RequestMethodType.GET;
		
		Url url = UrlFactory.FromString(l[1]);
		
		request.Address = url.Path;
		request.Queries = url.Queries;
		
		//Throw away the rest for now:
		while ((line = reader.readLine()) != null) {
			if (line.length() == 0)
				break;
		}
		
		return request;
	}
}
