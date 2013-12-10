package org.GreenTeaScript.DShell;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.GreenTeaScript.GtFunc;

public class DShellAuthenticator {
	
	public static String RECServerURL = "http://localhost:3001/api/2.0/";
	
	public static boolean RecordTestResult(boolean Result, GtFunc Func) {
		int Data = 0;
		String TestedFunctionName = Func.FuncName.replaceAll("Test_", "");
		String AuthId = System.getProperty("user.name");
		String Location;

		try {
			Location = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			Location = "localhost";
		}

		if(!Result) {
			Data = 1;
		}
		
		RecAPI.PushRawData(RECServerURL, TestedFunctionName, Location, Data, AuthId, "");
		System.out.println(RecAPI.GetLatestData(RECServerURL, TestedFunctionName, Location));

		return Result;
	}
	
	public static boolean AuthenticateFunction(String FunctionName) {
		return true;
	}

}
