package Utilities.Response;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class ImageResponse implements IResponse {

	private BufferedImage image;

	public ImageResponse(String path) throws IOException {
		File file = new File(path);
		System.out.println(path);
		image = ImageIO.read(file);
	}

	@Override
	public void WriteToStream(OutputStream stream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "jpg", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] bytes = baos.toByteArray();

		String message = "HTTP/1.1 200 OK\r\n" + "Content-Type: image/jpg\r\n" + "Content-Length: "+bytes.length+"\r\n\r\n";
		byte[] serverMessage = message.getBytes();

		for (int i = 0; i < serverMessage.length; i++) {
			try {
				stream.write(serverMessage[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			stream.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
