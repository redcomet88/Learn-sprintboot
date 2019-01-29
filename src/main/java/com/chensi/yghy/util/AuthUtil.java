package com.chensi.yghy.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class AuthUtil {

	public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException{
		JSONObject jsonObject=null;
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

	public static String getAccessToken(String APPID,String APPSECRET) throws  IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET + "";
		JSONObject backData = doGetJson(url);
		String accessToken=backData.getString("access_token");
		return accessToken;
	}

	public static String getJSApiTicket(String APPID,String APPSECRET) throws IOException{
		//ªÒ»°token
		String acess_token= getAccessToken(APPID,APPSECRET);
		String urlStr = //"http://gy7tgr.natappfree.cc//wxapi/weixinMall/index.jsp";
				"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+acess_token+"&type=jsapi";
		JSONObject backData=doGetJson(urlStr);
		String ticket = backData.getString("ticket");
		return  ticket;
	}
}
