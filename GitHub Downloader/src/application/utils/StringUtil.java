package application.utils;

public class StringUtil {
	
	public static String concatWith(String[] strArr, String spacer) {
		String concat = "";
		for(String str : strArr) {
			concat += spacer + str;
		}
		return concat;
	}

	public static String getFolderFromPath(String path) {
		if(path == null) return null;
		String[] arr = path.split("/", 0);
		return arr[arr.length - 1];
	}

	public static String getFileFromURL(String URL) {
		return getFolderFromPath(URL);
	}

	public static String getSiteFromURL(String URL) {
		if(URL == null) return null;
		String[] arr = removeURLPrefix(URL).split("/", 1);
		return arr[0];
	}

	public static String getPathFromURL(String URL) {
		if(URL == null) return null;
		String[] arr = removeURLPrefix(URL).split("/", 1);
		return arr[1];
	}
	
	public static String removeURLPrefix(String URL) {
		if(URL == null) return null;
		String[] arr = URL.split("//", 1);
		return arr[arr.length - 1];
	}
}