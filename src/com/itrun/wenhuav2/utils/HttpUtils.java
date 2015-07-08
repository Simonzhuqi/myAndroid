package com.itrun.wenhuav2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

public class HttpUtils {
	
	/**
	 * 根据参数 返回post的httpresponse
	 * @param url 请求的链接
	 * @param paramsMap  请求参数
	 * @return
	 */
	public static HttpResponse getHttpResponse(String url, HashMap<String, String> paramsMap) throws Exception
	{
		HttpResponse httpResponse = null;
		HttpPost httpRequest=null; 
	    List <NameValuePair> params=null; 
	    httpRequest = new HttpPost(url);
	    params = new ArrayList<NameValuePair>();
	    
	    if(paramsMap != null)
	    {
	    	for(Map.Entry entry : paramsMap.entrySet())
		    {
		    	String key = (String) entry.getKey();
		    	String value = (String) entry.getValue();
		    	params.add(new BasicNameValuePair(key, value));
		    }
	    }
    	httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
    	httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
		HttpClient client = new DefaultHttpClient();
		HttpParams params2 = null;
		params2 = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params2, 15000);
		HttpConnectionParams.setSoTimeout(params2, 15000);  
		httpResponse = client.execute(httpRequest);
		return httpResponse;
	}

}
