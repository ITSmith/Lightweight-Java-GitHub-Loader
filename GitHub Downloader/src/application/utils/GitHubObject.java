package application.utils;

public class GitHubObject {
	private String user;
	private String repo;
	private String type;
	private String branch;
	private String path;
	private String file;
	private boolean valid;

	public GitHubObject(String source) {
		String[] sourceArr = splitSource(source);
		if (sourceArr == null || sourceArr.length < 2)
			valid = false;
		else {
			valid = true;
			user = sourceArr[0];
			repo = sourceArr[1];
			branch = "master";
			type = "";
			if (sourceArr.length > 2) {
				type = sourceArr[2];
				branch = sourceArr[3];
				path = "/";
				file = "";
				if (sourceArr.length > 4 && sourceArr[sourceArr.length - 1].indexOf('.') != -1) {
					// Add file if exists
					file = sourceArr[sourceArr.length - 1];
					// Has File
					for (int i = 4; i < sourceArr.length - 1; i++) {
						path +=  sourceArr[i] + "/";
					}
				} else {
					// No file
					for (int i = 4; i < sourceArr.length; i++) {
						path += "/" + sourceArr[i];
					}
				}
			}
		}
	}

	private static String[] splitSource(String source) {
		String[] tempArr = source.split("//");
		if (tempArr.length > 2)
			return null;
		tempArr = tempArr[tempArr.length - 1].split("/", 2);
		if (!tempArr[0].toLowerCase().equals("github.com"))
			return null;
		return tempArr[1].split("/");
	}
	
	public String getRawURL() {
		if(!valid || file.equals("")) return null;
		else return "https://raw.githubusercontent.com/"+user+"/"+repo+"/"+branch+path+file;
	}
	
	public String getRepoURL() {
		if(!valid) return null;
		else return "https://github.com/"+user+"/"+repo+"/archive/"+branch+".zip";
	}
	
	public String getRepo() {
		return repo;
	}
	
	public String getType() {
		return type;
	}
	
	public String getBranch() {
		return branch;
	}
	
	public String getFile() {
		return file;
	}
	
	public boolean getValid() {
		return valid;
	}
}