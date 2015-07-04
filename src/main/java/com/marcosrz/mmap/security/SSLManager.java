package com.marcosrz.mmap.security;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import com.marcosrz.mmap.security.exception.MMAPSecurityException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;

public class SSLManager {

	private static SSLManager instance;
	
	public static SSLManager getInstance(){
		if (instance == null)
			instance = new SSLManager();
		return instance;
	}
	
	public Client getSSLRestClient() throws MMAPSecurityException{
		Client c;
		
	    TrustManager trustManager = new MMAPTrustManager();

	    SSLContext context;
	    
		try {
			context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] {trustManager} , new SecureRandom());
			
		    HttpsURLConnection.setDefaultSSLSocketFactory(context
		            .getSocketFactory());
		    ClientConfig config = new DefaultClientConfig();
		    
		    config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
		            new HTTPSProperties(new HostnameVerifier() {
		                public boolean verify(String s, SSLSession sslSession) {
		                    return true;
		                }
		            }, context));
		    
		     c = Client.create(config);
		    
		} catch (NoSuchAlgorithmException e) {
			throw new MMAPSecurityException("");
		} catch (KeyManagementException e) {
			throw new MMAPSecurityException("");
		}
		
		return c;
	}
}
