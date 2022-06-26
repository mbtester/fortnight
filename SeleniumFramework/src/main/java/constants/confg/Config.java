package constants.confg;

import constants.path.Path;
import utils.FileOperations;

public interface Config {
	
	/**VARIABLES**/

	//APP CONFIG DATA
	String BROWSER_NAME = FileOperations.getConfigValue(Path.CONFIG_FILE_PATH, "browserName");
	String URL = FileOperations.getConfigValue(Path.CONFIG_FILE_PATH, "url");
	String USER_NAME = FileOperations.getConfigValue(Path.CONFIG_FILE_PATH, "userName");
	String PASSWORD = FileOperations.getConfigValue(Path.CONFIG_FILE_PATH, "password");
}
