package org.GreenTeaScript.DShell;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class RecAPI {
	
	private static String RemoteProcedureCall(String RECServerURL, String Method, String Params) throws IOException {
		double JsonRpcVersion = 2.0;
		int Id = 0;
		
		String Json = new String("{ \"jsonrpc\": \""+JsonRpcVersion+"\", "
									+ "\"method\": \""+Method+"\", "
									+ "\"params\": "+Params+", "
									+ "\"id\": "+Id+" }");
		StringEntity Body = new StringEntity(Json);
		
		HttpClient Client = HttpClientBuilder.create().build();
		HttpPost Request = new HttpPost(RECServerURL);
		Request.addHeader("Content-type", "application/json");
		Request.setEntity(Body);
		HttpResponse Response = Client.execute(Request);
		HttpEntity Entity = Response.getEntity();

		String ReturnValue = EntityUtils.toString(Entity);
		// TODO validate ReturnValue
		
		return ReturnValue;
	}
	
	public static String PushRawData(String RECServerURL, String Type, String Location, int Data, String AuthId, String Context) {
		String Params = "{ \"type\": \""+Type+"\", "
							+ "\"location\": \""+Location+"\", "
							+ "\"data\": "+Data+", "
							+ "\"authid\": \""+AuthId+"\", "
							+ "\"context\": \""+Context+"\" }";
		
		String Response = "";
		
		try {
			Response = RemoteProcedureCall(RECServerURL, "pushRawData", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return Response;
	}
	
	public static String GetLatestData(String RECServerURL, String Type, String Location) {
		String Params = "{ \"type\": \""+Type+"\", "
							+ "\"location\": \""+Location+"\" }";
		
		String Response = "";

		try {
			Response = RemoteProcedureCall(RECServerURL, "getLatestData", Params);
		}
		catch(IOException e) {
			// TODO exception handling
		}
		
		// TODO validate Response
		
		return Response;
	}
	
}
