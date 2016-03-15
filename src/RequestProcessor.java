import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import ServerStack.Request;
import ServerStack.ServerStackTop;
import ServerStack.Response.IResponse;
import ServerStack.Response.TextResponse;

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
		String line;
		
		while ((line = br.readLine()) != null) {
	          if (line.length() == 0)
	            break;
	          System.out.println(line);
	        }
		
		ServerStackTop stack = new ServerStackTop();
		
		IResponse response = stack.Process(new Request());
		System.out.println(response);
		
		response.WriteToStream(outStream);
        br.close(); // Close the input stream
        socket.close(); // Close the socket itself
	}
	
}
