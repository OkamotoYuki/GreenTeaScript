package org.GreenTeaScript.DShell;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.GreenTeaScript.GtFunc;

public class DShellAuthenticator {
	
	public static String RECServerURL = "http://localhost:3001/api/2.0/";
	private static Map<String, Boolean> AuthenticatedFunctionMap = new HashMap<String, Boolean>();
	
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
	
	public static void InitAuthenticatedFunctionMap() {
		AuthenticatedFunctionMap.put("print", true);
		AuthenticatedFunctionMap.put("println", true);
		AuthenticatedFunctionMap.put("assert", true);
		AuthenticatedFunctionMap.put("func1", true); // FIXME
		AuthenticatedFunctionMap.put("func2", false); // FIXME

	}
	
	public static boolean AuthenticateFunction(String FunctionName) {
		if(AuthenticatedFunctionMap.containsKey(FunctionName)
			&& AuthenticatedFunctionMap.get(FunctionName)) {
			return true;
			
		}
		return false;
	}

}
