import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import Services.ServerStackTop;
import Utilities.Request.Request;
import Utilities.Request.RequestFactory;
import Utilities.Response.IResponse;
import Utilities.Response.TextResponse;

public class RequestProcessor implements Runnable {
 
	private Socket socket;

	public RequestProcessor(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processRequest() throws Exception {
		InputStream inStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
		
		Request request = RequestFactory.ReadFromBufferedReader(br);
		ServerStackTop stack = new ServerStackTop();
		
		IResponse response = stack.Process(request);
		
		response.WriteToStream(outStream);
        br.close(); // Close the input stream
        socket.close(); // Close the socket itself
	}
	
}
