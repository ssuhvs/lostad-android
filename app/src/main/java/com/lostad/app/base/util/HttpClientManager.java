package com.lostad.app.base.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
/**
 * 统一管理httpClient
 * @author sszvip@qq.com
 * @since  2014-7-2
 */
public class HttpClientManager {
	private static HttpClient httpClientGlobal = null;
	public static HttpClient createHttpClient() {
		// Create and initialize HTTP parameters
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, 100);
		// HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		// Create and initialize scheme registry
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		// schemeRegistry.register(new Scheme("https",
		// SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,schemeRegistry);
		DefaultHttpClient tempClient = new DefaultHttpClient(cm, params);
		tempClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,	30000); // 
		tempClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 60000);// 
		
		String proxyHost = android.net.Proxy.getDefaultHost();
		if( proxyHost != null && !proxyHost.equals("")) {//
			HttpHost proxy = new HttpHost(android.net.Proxy.getDefaultHost(),android.net.Proxy.getDefaultPort());
			tempClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY,proxy);
		}

		return tempClient;
	}

	
	public static synchronized HttpClient getHttpClient(boolean isSingleton) {
		if (isSingleton) {
			httpClientGlobal = getHttpClient();
		} else {
			httpClientGlobal =  createHttpClient() ;
		}
		return httpClientGlobal;
	}// end method

	private static synchronized HttpClient getHttpClient() {
		if (httpClientGlobal == null) {
			httpClientGlobal = createHttpClient();
		}
		return httpClientGlobal;
	}// end method

	
	public static void reset() {
		try{
		httpClientGlobal.getConnectionManager().shutdown();
		httpClientGlobal = null;		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	  private static HttpClient clientSSL = null;
	    /**
	     * 初始化HttpClient对象
	     * @param params
	     * @return
	     */
	    public static synchronized HttpClient getHttpClientSSL(HttpParams params) {
	        if(clientSSL == null){
	            try {
	                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	                trustStore.load(null, null);
	                 
	                SSLSocketFactory sf = new SSLSocketFactoryImp(trustStore);
	                //允许所有主机的验证
	                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	                 
	                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	                HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
	                // 设置http和https支持
	                SchemeRegistry registry = new SchemeRegistry();
	                registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	                registry.register(new Scheme("https", sf, 443));
	                 
	                ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
	                 
	                clientSSL = new DefaultHttpClient(ccm, params);
	            } catch (Exception e) {
	                e.printStackTrace();
	                clientSSL = new DefaultHttpClient(params);
	            }
	        }
	        return clientSSL;
	    }
	 
	   public static class SSLSocketFactoryImp extends SSLSocketFactory {
	        final SSLContext sslContext = SSLContext.getInstance("TLS");
	 
	        public SSLSocketFactoryImp(KeyStore truststore)
	                throws NoSuchAlgorithmException, KeyManagementException,
	                KeyStoreException, UnrecoverableKeyException {
	            super(truststore);
	 
	            TrustManager tm = new X509TrustManager() {
	                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	 
	                @Override
	                public void checkClientTrusted(
	                        java.security.cert.X509Certificate[] chain,
	                        String authType)
	                        throws java.security.cert.CertificateException {
	                }
	 
	                @Override
	                public void checkServerTrusted(
	                        java.security.cert.X509Certificate[] chain,
	                        String authType)
	                        throws java.security.cert.CertificateException {
	                }
	            };
	            sslContext.init(null, new TrustManager[] { tm }, null);
	        }
	 
	        @Override
	        public Socket createSocket(Socket socket, String host, int port,
	                boolean autoClose) throws IOException {
	            return sslContext.getSocketFactory().createSocket(socket, host,
	                    port, autoClose);
	        }
	 
	        @Override
	        public Socket createSocket() throws IOException {
	            return sslContext.getSocketFactory().createSocket();
	        }
	    }
}
