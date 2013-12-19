package org.GreenTeaScript.DShell;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.GreenTeaScript.GtFunc;

public class DShellAuthenticator {
	
	public static String RECServerURL = "http://localhost:3001/api/3.0/";
	public static String TestVersion = null;
	private static Map<String, Boolean> AuthenticatedFunctionMap = new HashMap<String, Boolean>();
	
	public static boolean RecordTestResult(boolean Result, GtFunc Func) {
		String User = System.getProperty("user.name");
		String Host;
		String TestedFunctionName = Func.FuncName.replaceAll("Test_", "");

		try {
			Host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			Host = "localhost";
		}
	
		RecAPI.PushTestResult(RECServerURL, User, Host, TestVersion, TestedFunctionName, Result);

		return Result;
	}
	
	public static void InitAuthenticatedFunctionMap() {
		String User = System.getProperty("user.name");
		String Host;

		try {
			Host = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			Host = "localhost";
		}
		
		AuthenticatedFunctionMap.put("print", true);
		AuthenticatedFunctionMap.put("println", true);
		AuthenticatedFunctionMap.put("assert", true);

		List<Map<String, Object>> AuthenticatedFunctionList = RecAPI.GetTestResultList(RECServerURL, User, Host, TestVersion);
		for(int i = 0; i < AuthenticatedFunctionList.size(); i++) {
			Map<String, Object> AuthenticatedFunction = AuthenticatedFunctionList.get(i);
			AuthenticatedFunctionMap.put((String)AuthenticatedFunction.get("funcname"), (Boolean)AuthenticatedFunction.get("result"));
		}
	}
	
	public static boolean AuthenticateFunction(String FunctionName) {
		if(AuthenticatedFunctionMap.containsKey(FunctionName)
			&& AuthenticatedFunctionMap.get(FunctionName)) {
			return true;
			
		}
		return false;
	}

}
