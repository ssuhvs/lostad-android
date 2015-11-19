/*
 * Copyright 2010 Renren, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lostad.app.base.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

/**
 * 请求服务器工具类
 * @author sszvip@qq.com
 * @since  2013-7-2
 */
public class RequestUtil {
	
	public static String getRequest(final String url,final boolean isSingleton) throws Exception {
		String json = null;
		HttpClient client = HttpClientManager.getHttpClient(isSingleton);
		HttpEntity resEntity = null ;
		HttpGet get = new HttpGet(url);
		try {
			//long t1 = System.currentTimeMillis();
			HttpResponse response = client.execute(get,new BasicHttpContext());
			resEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 200
				//取出回应字串
				json = EntityUtils.toString(resEntity);
			} 
		} catch (Exception e) {
			if (get != null){
				get.abort();
			}
			throw e;
		} finally {
			
			if (resEntity != null) {
				try {
					resEntity.consumeContent();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			client.getConnectionManager().closeExpiredConnections();
		}
		return json;
	}// end method submitForm
	
	public static String postRequest(final String url, final StringEntity params,final boolean isSingleton) throws Exception {
		String json = null;
		HttpClient client = HttpClientManager.getHttpClient(isSingleton);
		HttpEntity resEntity = null ;
		HttpPost post = new HttpPost(url);
		try {
			//long t1 = System.currentTimeMillis();

			if (params != null) {
				params.setContentEncoding(HTTP.UTF_8);
				params.setContentType("application/x-www-form-urlencoded");
				post.setEntity(params);
			}

			HttpResponse response = client.execute(post,new BasicHttpContext());
			resEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 200
				//取出回应字串
				json = EntityUtils.toString(resEntity);
			} 
		} catch (Exception e) {
			if (post != null){
				post.abort();
			}
			throw e;
		} finally {
			
			if (resEntity != null) {
				try {
					resEntity.consumeContent();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			client.getConnectionManager().closeExpiredConnections();
		}
		return json;
	}// end method submitForm

	public static String postForm(String url, List<NameValuePair> params,
			boolean isSingleton) throws Exception {
		String json = null;
		HttpClient client = HttpClientManager.getHttpClient(isSingleton);
		HttpEntity resEntity = null ;
		HttpPost post = new HttpPost(url);
		try {
			
			if (params != null) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				post.setEntity(entity);
			}
			HttpResponse response = client.execute(post,new BasicHttpContext());
			resEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 200
				// 取出回应字串
				json = EntityUtils.toString(resEntity);
			} 
		} catch (Exception e) {
			if (post != null){
				post.abort();//终止请求
			}
			throw e;
		} finally {
			
			if (resEntity != null) {
				try {
					resEntity.consumeContent();//释放资源
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			client.getConnectionManager().closeExpiredConnections();
			///client.getConnectionManager().shutdown();
		}
		return json;
		
	}

	public static String postJson(String url,Object obj) throws Exception {
		Gson g = new Gson();
		String json = g.toJson(obj);
		return postJson(url, json);
	}
	public static String postJson(String url, String dataJson) throws Exception {
		String json = null;
		HttpEntity resEntity = null ;
		HttpClient client = HttpClientManager.getHttpClient(true);
		HttpPost post = new HttpPost(url);
		try {
			post.addHeader("Content-Type","application/json;charset=UTF-8");
			StringEntity se = new StringEntity(dataJson,"UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			//请求
			HttpResponse response = client.execute(post,new BasicHttpContext());
			resEntity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {// 200
				// 取出回应字串
				json = EntityUtils.toString(resEntity);
			} 
		} catch (Exception e) {
			if (post != null){
				post.abort();//终止请求
			}
			throw e;
		} finally {
			
			if (resEntity != null) {
				try {
					resEntity.consumeContent();//释放资源
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			client.getConnectionManager().closeExpiredConnections();
			///client.getConnectionManager().shutdown();
		}
		return json;
	}
	

	
	
	/** 
	 * 直接通过HTTP协议提交数据到服务器,实现表单提交功能 
	 * @param actionUrl 上传路径 
	 * @param params 请求参数 key为参数名,value为参数值 
	 * @param file 上传文件 
	 * @throws Exception 
	 */  
	public static String postFile(String actionUrl, Map<String, String> params, FormFile[] files) throws Exception {  
	    try {             
	        String BOUNDARY = "---------7d4a6d158c9"; //数据分隔线  
	        String MULTIPART_FORM_DATA = "multipart/form-data";  
	          
	        URL url = new URL(actionUrl);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setDoInput(true);//允许输入  
	        conn.setDoOutput(true);//允许输出  
	        conn.setUseCaches(false);//不使用Cache  
	        conn.setRequestMethod("POST");            
	        conn.setRequestProperty("Connection", "Keep-Alive");  
	        conn.setRequestProperty("Charset", "UTF-8");  
	        conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + "; boundary=" + BOUNDARY);  
	  
	        //上传的表单参数部分，格式请参考文章  
	        StringBuilder sb = new StringBuilder();  
	        if(params!=null){
	        	 for (Map.Entry<String, String> entry : params.entrySet()) {//构建表单字段内容  
	 	            sb.append("--");  
	 	            sb.append(BOUNDARY);  
	 	            sb.append("\r\n");  
	 	            sb.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");  
	 	            sb.append(entry.getValue());  
	 	            sb.append("\r\n");  
	 	        }  
	        }
	       
	        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());  
	        outStream.write(sb.toString().getBytes());//发送表单字段数据  
	         
	        //上传的文件部分，格式请参考文章  
	        for(FormFile file : files){  
	            StringBuilder split = new StringBuilder();  
	            split.append("--");  
	            split.append(BOUNDARY);  
	            split.append("\r\n");  
	            split.append("Content-Disposition: form-data;name=\""+ file.getFormname()+"\";filename=\""+ file.getFilname() + "\"\r\n");  
	            split.append("Content-Type: "+ file.getContentType()+"\r\n\r\n");  
	            outStream.write(split.toString().getBytes());  
	            outStream.write(file.getData(), 0, file.getData().length);  
	            outStream.write("\r\n".getBytes());  
	        }  
	        byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();//数据结束标志           
	        outStream.write(end_data);  
	        outStream.flush();  
	        int cah = conn.getResponseCode();  
	        if (cah != 200) throw new RuntimeException("请求url失败");  
	        InputStream is = conn.getInputStream();  
	        int ch;  
	        StringBuilder b = new StringBuilder();  
	        while( (ch = is.read()) != -1 ){  
	            b.append((char)ch);  
	        }  
	        outStream.close();  
	        conn.disconnect();  
	        return b.toString();  
	    } catch (Exception e) {  
	    	
	        throw e;  
	    }  
	} 
	
	//SSL
	/**
     * Post请求连接Https服务
     * @param serverURL  请求地址
     * @param jsonStr    请求报文
     * @return
     * @throws Exception
     */
    public static synchronized String postHttps(String url, String json)throws Exception {
        // 参数
        HttpParams httpParameters = new BasicHttpParams();
        // 设置连接超时
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
        // 设置socket超时
        HttpConnectionParams.setSoTimeout(httpParameters, 3000);
        // 获取HttpClient对象 （认证）
        HttpClient hc = HttpClientManager.getHttpClientSSL(httpParameters);
        HttpPost post = new HttpPost(url);
        // 发送数据类型
        post.addHeader("Content-Type", "application/json;charset=utf-8");
        // 接受数据类型
        post.addHeader("Accept", "application/json");
        // 请求报文
        StringEntity entity = new StringEntity(json, "UTF-8");
        post.setEntity(entity);
        post.setParams(httpParameters);
        HttpResponse response = null;
        try {
            response = hc.execute(post);
        } catch (UnknownHostException e) {
            throw new Exception("Unable to access " + e.getLocalizedMessage());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        int sCode = response.getStatusLine().getStatusCode();
        if (sCode == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity());
        } else
            throw new Exception("StatusCode is " + sCode);
    }
 
   
}
