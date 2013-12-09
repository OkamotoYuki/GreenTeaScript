package org.GreenTeaScript.DShell;
import org.GreenTeaScript.GtFunc;

public class DShellAuthenticator {
	
	public static boolean RecordTestResult(boolean Result, GtFunc Func) {
		System.out.println(Result);
		String TestedFunctionName = Func.FuncName.replaceAll("Test_", "");
		System.out.println(TestedFunctionName);
		return Result;
	}
	
	public static boolean AuthenticateFunction(String FunctionName) {
		return true;
	}

}
