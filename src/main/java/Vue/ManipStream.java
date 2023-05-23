package Vue;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ManipStream {
	byte[] buffer = new byte[1024];

	int bytesRead;
	InputStream in;
	ImageInputStream imagesIn;
	BufferedImage myImage;

	public BufferedImage OuvreStream(String s) {
		/* Load Assets */
		try {
			in = ClassLoader.getSystemClassLoader().getResourceAsStream("src/main/resources/assets/" + s + ".png");
			if(in != null) {
				imagesIn = ImageIO.createImageInputStream(in);
			} else {
				System.err.println("InputStream in is null");
			}
			in.close();
		}
		catch (IllegalArgumentException e) {
			// Handle the specific exception
			System.err.println("Invalid image or unsupported format: " + e.getMessage());
		}
		catch (NullPointerException | IOException e) {
		}

		try {
			myImage = ImageIO.read(imagesIn);
			imagesIn.close();

		} catch (IllegalArgumentException e) {
			// Handle the specific exception
			System.err.println("Invalid image or unsupported format: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Erreur dans le chargement des images");
			throw new RuntimeException(e);
		}
		if(myImage != null)
			return myImage;
		else
			return null;
	}

	public static void afficheClassPath() {
		String classpath = System.getProperty("java.class.path");
		String[] classpathEntries = classpath.split(System.getProperty("path.separator"));
		for (String entry : classpathEntries) {
			System.out.println(entry);
		}
	}
}
