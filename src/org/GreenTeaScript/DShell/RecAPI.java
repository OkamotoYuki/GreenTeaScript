package org.GreenTeaScript.DShell;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.GreenTeaScript.LibGreenTea;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RecAPI {
	
	/* API for AssureNote */
	private static Object RemoteProcedureCall(String RECServerURL, String Method, String Params) throws IOException {
		double JsonRpcVersion = 2.0;
		int Id = 0;
		
		String Json = new String("{ \"jsonrpc\": \""+JsonRpcVersion+"\", "
									+ "\"method\": \""+Method+"\", "
									+ "\"params\": "+Params+", "
									+ "\"id\": "+Id+" }");
		StringEntity RequestBody = new StringEntity(Json);
		
		// connect to REC
		HttpClient Client = HttpClientBuilder.create().build();
		HttpPost Request = new HttpPost(RECServerURL);
		Request.addHeader("Content-type", "application/json");
		Request.setEntity(RequestBody);
		HttpResponse Response = Client.execute(Request);
		HttpEntity Entity = Response.getEntity();
		String ResponseBody = EntityUtils.toString(Entity);

		// validate response
		ObjectMapper Mapper = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> ResponseBodyMap = Mapper.readValue(ResponseBody, Map.class);
		if(ResponseBodyMap == null) {
			return null;
		}
		if(!ResponseBodyMap.containsKey("id")) {
			LibGreenTea.Exit(1, "jsonrpc must have property 'id'");
		}
		if(!ResponseBodyMap.containsKey("jsonrpc")) {
			LibGreenTea.Exit(1, "jsonrpc must have property 'jsonrpc'");
		}
		if(Float.parseFloat((String)ResponseBodyMap.get("jsonrpc")) != 2.0) {
			LibGreenTea.Exit(1, "dshell supports jsonrpc 2.0");
		}
		if(!ResponseBodyMap.containsKey("result")) {
			LibGreenTea.Exit(1, "jsonrpc must have property 'result'");
		}

		return ResponseBodyMap.get("result");
	}
	
	public static Map<String, Object> PushRawData(String RECServerURL, String Type, String Location, int Data, String AuthId, String Context) {
		String Params = "{ \"type\": \""+Type+"\", "
							+ "\"location\": \""+Location+"\", "
							+ "\"data\": "+Data+", "
							+ "\"authid\": \""+AuthId+"\", "
							+ "\"context\": \""+Context+"\" }";
		
		Map<String, Object> ReturnValue = null;
		
		try {
			ReturnValue = (Map<String, Object>)RemoteProcedureCall(RECServerURL, "pushRawData", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return ReturnValue;
	}
	
	public static Map<String, Object> GetLatestData(String RECServerURL, String Type, String Location) {
		String Params = "{ \"type\": \""+Type+"\", "
							+ "\"location\": \""+Location+"\" }";
		
		Map<String, Object> ReturnValue = null;

		try {
			ReturnValue = (Map<String, Object>)RemoteProcedureCall(RECServerURL, "getLatestData", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return ReturnValue;
	}
	
	/* API for DShell */
	public static Map<String, Object> PushTestResult(String RECServerURL, String User, String Host, String Version, String Funcname, boolean Result) {
		String Params = "{ \"user\": \""+User+"\", "
							+ "\"host\": \""+Host+"\", "
							+ "\"version\": \""+Version+"\", "
							+ "\"funcname\": \""+Funcname+"\", "
							+ "\"result\": "+Result+" }";
		
		Map<String, Object> ReturnValue = null;
		
		try {
			ReturnValue = (Map<String, Object>)RemoteProcedureCall(RECServerURL, "pushTestResult", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return ReturnValue;
	}
	
	public static Map<String, Object> GetTestResult(String RECServerURL, String User, String Host, String Version, String Funcname) {
		String Params = "{ \"user\": \""+User+"\", "
							+ "\"host\": \""+Host+"\", "
							+ "\"version\": \""+Version+"\", "
							+ "\"funcname\": \""+Funcname+"\" }";
		
		Map<String, Object> ReturnValue = null;
		
		try {
			ReturnValue = (Map<String, Object>)RemoteProcedureCall(RECServerURL, "getTestResult", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return ReturnValue;
	}

	public static List<Map<String, Object>> GetTestResultList(String RECServerURL, String User, String Host, String Version) {
		String Params = "{ \"user\": \""+User+"\", "
							+ "\"host\": \""+Host+"\", "
							+ "\"version\": \""+Version+"\" }";
		
		List<Map<String, Object>> ReturnValue = null;
		
		try {
			ReturnValue = (List<Map<String, Object>>)RemoteProcedureCall(RECServerURL, "getTestResultList", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return ReturnValue;
	}
	
}
