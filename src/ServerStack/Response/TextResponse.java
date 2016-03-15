package ServerStack.Response;

import java.io.OutputStream;
import java.io.PrintWriter;

public class TextResponse implements IResponse {

	public String status;
	public String body;
	
	public TextResponse(String status, String body) {
		this.status = status;
		this.body = body;
	}

	@Override
	public void WriteToStream(OutputStream stream) {
		PrintWriter out = new PrintWriter(stream);

		
		out.println("HTTP/1.1 "+status); // Version & status code
        out.println("Content-Type: text/plain"); // The type of data
        out.println(); // End of headers

        out.print(body);
                
        out.flush();
	}
	
	
	@Override
	public String toString() {
		return "TextResponse:\nStatus:\t"+status+"\n"+body+"\n============================";
	}
}
