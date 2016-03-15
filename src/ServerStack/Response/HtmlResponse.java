package ServerStack.Response;

import java.io.OutputStream;
import java.io.PrintWriter;

public class HtmlResponse implements IResponse {

	@Override
	public void WriteToStream(OutputStream stream) {
		PrintWriter out = new PrintWriter(stream);

		out.println("HTTP/1.1 200 OK"); // Version & status code
        out.println("Content-Type: text/html"); // The type of data
        out.println(); // End of headers

        out.print("<html>hi</html>\n<img src=\"image/s/459\">");
                
        out.flush();
	}

}
