package application.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadUtil {

	public static void download(URL url, File file) {
		try {
			InputStream input = url.openStream();
			if (file.exists()) {
				if (file.isDirectory())
					throw new IOException("File '" + file + "' is a directory");
				if (!file.canWrite())
					throw new IOException("File '" + file + "' cannot be written");
			} else {
				File parent = file.getParentFile();
				if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
					throw new IOException("File '" + file + "' could not be created");
				}
			}
			FileOutputStream output = new FileOutputStream(file);
			byte[] b = new byte[4096];
			int i = 0;

			while ((i = input.read(b)) != -1)
				output.write(b, 0, i);

			input.close();
			output.close();
			// System.out.println("File '" + file + "' downloaded successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}