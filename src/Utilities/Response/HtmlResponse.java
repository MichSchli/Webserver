package Utilities.Response;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

import IO.IFileHandler;

public class HtmlResponse implements IResponse {

	private String Address;
	private IFileHandler _fileHandler;

	public HtmlResponse(String string, IFileHandler fileHandler) {
		this.Address = string;
		this._fileHandler = fileHandler;
	}

	@Override
	public void WriteToStream(OutputStream stream){
		PrintWriter out = new PrintWriter(stream);

		out.println("HTTP/1.1 200 OK"); // Version & status code
        out.println("Content-Type: text/html"); // The type of data
        out.println(); // End of headers

        try {
			for (String line : _fileHandler.readLines(Address)) {
				out.println(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
                
        out.flush();
	}

}
