import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {
	
	public static void main(String[] args) throws IOException {
		ServerSocket welcomeSocket = new ServerSocket(9001);
				
		while(true){
			Socket connectionSocket = welcomeSocket.accept();
			
			System.out.println("accepted");
			
			RequestProcessor requestProcessor = new RequestProcessor(connectionSocket);
			Thread thread = new Thread(requestProcessor);
			thread.start();
			
			System.out.println("started thread, listening again...");
		}
	}
}
