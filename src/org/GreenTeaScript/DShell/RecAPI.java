package org.GreenTeaScript.DShell;

import java.io.IOException;
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
	
	private static Map<String, Object> RemoteProcedureCall(String RECServerURL, String Method, String Params) throws IOException {
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

		if(ResponseBodyMap.get("result") == null) {
			return null;
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> ReturnValue = (Map<String, Object>)ResponseBodyMap.get("result");
		
		return ReturnValue;
	}
	
	public static Map<String, Object> PushRawData(String RECServerURL, String Type, String Location, int Data, String AuthId, String Context) {
		String Params = "{ \"type\": \""+Type+"\", "
							+ "\"location\": \""+Location+"\", "
							+ "\"data\": "+Data+", "
							+ "\"authid\": \""+AuthId+"\", "
							+ "\"context\": \""+Context+"\" }";
		
		Map<String, Object> Result = null;
		
		try {
			Result = RemoteProcedureCall(RECServerURL, "pushRawData", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return Result;
	}
	
	public static Map<String, Object> GetLatestData(String RECServerURL, String Type, String Location) {
		String Params = "{ \"type\": \""+Type+"\", "
							+ "\"location\": \""+Location+"\" }";
		
		Map<String, Object> Result = null;

		try {
			Result = RemoteProcedureCall(RECServerURL, "getLatestData", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return Result;
	}
	
}
