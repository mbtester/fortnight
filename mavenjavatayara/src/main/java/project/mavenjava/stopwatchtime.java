package project.mavenjava;

import org.apache.commons.lang.time.StopWatch;
import org.testng.Reporter;

public class stopwatchtime extends StopWatch {
	static StopWatch stopwatch = new StopWatch();
	public static void timetaken(){
	long x = stopwatch.getTime();
	// Convert the result to a string
	String numberAsString = Long.toString(x);
	System.out.println("Execution time for this method: " + numberAsString + " milliseconds \n");
	Reporter.log("time taken to execute this method:"+ numberAsString +" milliseconds \n" );
	}
public static void start(String methodname) {
	stopwatch.start();
	Reporter.log(methodname+"; \n");
}
public static void stop(String methodname) {
	stopwatch.stop();
	Reporter.log(methodname+ "; \n");
}
public static void reset(String methodname) {
	stopwatch.reset();
	Reporter.log(methodname+"; \n");
}
}

