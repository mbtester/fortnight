package constants.testdata;

import constants.path.Path;
import utils.FileOperations;

public interface Excel {

	// EXCEL TEST DATA
	String USER_NAME = getExcelData("UserName");
	String PASSWORD = getExcelData("Password");
	
	// METHODS
	/*
	 * Read Excel Data
	 */
	
	public static String getExcelData(String key) {
		return FileOperations.readColumnValueUsingKeyFromExcel(Path.TESTDATA_PATH, "ExcelTestData.xlsx", "Details", key);
	}
}
