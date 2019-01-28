package com.chensi.yghy.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AuthUtil {
	//已经是自己的appid了 开发者
	public static final String APPID="wxc5b6f8b9184cc098";
	public static final String APPSECRET="cf0a97fee84550dc3e9378eaedc2e2b9";

	public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException{
		JSONObject jsonObject=null;
		//过时了
		//DefaultHttpClient client=new DefaultHttpClient();
		HttpClient client= HttpClientBuilder.create().build();
		HttpGet httpGet=new HttpGet(url);
		HttpResponse response=client.execute(httpGet);
		HttpEntity entity=response.getEntity();
		if(entity!=null){
			String result=EntityUtils.toString(entity,"UTF-8");
			jsonObject=JSONObject.fromObject(result);
		}
		httpGet.releaseConnection();
		return jsonObject;
	}
}
