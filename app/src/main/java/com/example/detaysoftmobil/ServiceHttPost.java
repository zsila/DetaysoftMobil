package com.example.detaysoftmobil;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;


public class ServiceHttPost {

	public static InputStream callLoginControlService(String url,String envelope) throws IOException {		
		
		Credentials defaultcreds = new UsernamePasswordCredentials("p0427", "1234");
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), defaultcreds);

		HttpPost httpPost = new HttpPost(url);
		StringEntity se = new StringEntity(envelope, HTTP.UTF_8);
		se.setContentType("text/xml");

		httpPost.setEntity(se);
		HttpResponse response = httpclient.execute(httpPost);
		HttpEntity resEntity = response.getEntity();

		return resEntity.getContent();
	
	}	
	

		
}
 