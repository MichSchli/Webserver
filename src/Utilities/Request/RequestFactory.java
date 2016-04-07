package Utilities.Request;

import java.io.BufferedReader;
import java.io.IOException;

import Utilities.Pattern;

public class RequestFactory {

	public static Request ReadFromBufferedReader(BufferedReader reader) throws IOException {
	
		Request request = new Request();
		
		//Read the request line:
		String line = reader.readLine();
				
		String[] l = line.split(" ");
		
		request.Method = l[0];
		request.Address = new Pattern(l[1].split("/"), 1);

		//Throw away the rest for now:
		while ((line = reader.readLine()) != null) {
			if (line.length() == 0)
				break;
		}
		
		return request;
	}
}
