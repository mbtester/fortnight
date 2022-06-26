package constants.path;

import java.io.File;

public interface Path {
	
	String PROJECT_PATH=System.getProperty("user.dir");
	String CONFIG_FILE_PATH = "src/main/java/config/AppConfig.properties";
	String CONFIG_LOG4J_FILE_PATH = "src/main/java/config/log4j.properties";
	String TESTDATA_PATH=PROJECT_PATH+File.separator+"testdata"+File.separator;
}
