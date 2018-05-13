package application.utils;

import java.io.File;

public class DownloadUtil {
	public static boolean download(String source, String destination) {
		File destFile = new File(destination);
		String url = parseURL(source);
		if(destFile.isDirectory()) {
			
			return true;
		} else {
			return false;
		}
	}
	
	public static String parseURL(String URL) {
		
		return null;
	}
}