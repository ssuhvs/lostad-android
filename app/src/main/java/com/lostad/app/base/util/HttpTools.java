package com.lostad.app.base.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * java.net.URL工具类
 * 
 * @author shimiso
 */
public class HttpTools {

	/**
	 * 
	 * 使用HTTP的POST方法提交xml数据.
	 * 
	 * @param xml
	 *            提交的xml数据
	 * @param urlPath
	 *            请求路径
	 * @return
	 * @author shimiso
	 * @update Feb 7, 2012 7:04:15 PM
	 */
	public static InputStream postXml(String xml, String urlPath) {
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			byte[] buff = xml.getBytes("UTF-8");
			conn.setConnectTimeout(10 * 1000);
			conn.setDoOutput(true); // 允许输出
			conn.setUseCaches(false); // 不允许缓存
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(buff.length));
			conn.setRequestProperty("content-type", "text/html");
			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			outStream.write(buff);
			outStream.flush();
			outStream.close();
			if (conn.getResponseCode() == 200) {
				// printResponse(conn);
				return conn.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用HTTP的POST方法提交的表单.
	 * 
	 * @param urlPath
	 *            请求路径
	 * @param params
	 *            请求参数
	 * @param encoding
	 *            请求参数编码
	 * @return 返回InputStream
	 * @throws Exception
	 * @author shimiso
	 * @update May 19, 2011 12:33:44 AM
	 */
	public static InputStream postForm(String urlPath,
			Map<String, String> params, String encoding) {
		try {
			StringBuilder sb = new StringBuilder();
			for (Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), encoding));
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
			byte[] data = sb.toString().getBytes();
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(6 * 1000);
			conn.setDoOutput(true);// 发送post请求必须设置允许输出
			conn.setUseCaches(false);// 不适用Cache
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			DataOutputStream dataOutStream = new DataOutputStream(
					conn.getOutputStream());
			dataOutStream.write(data);
			dataOutStream.flush();
			dataOutStream.close();
			if (conn.getResponseCode() == 200) {
				printResponse(conn);
				return conn.getInputStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 使用HTTP的POST方法单个上传文件.
	 * 
	 * @param filePath
	 *            文件路径
	 * @param urlPath
	 *            请求路径
	 * @author shimiso
	 * @update Feb 7, 2012 6:13:29 PM
	 */
	public static void postFile(String urlPath, String filePath) {
		try {
			URL url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setChunkedStreamingMode(1024 * 1024);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			File file = new File(filePath);
			conn.setRequestProperty("Content-Type", "multipart/form-data;file="
					+ URLEncoder.encode(file.getName(), "UTF-8"));

			OutputStream out = new DataOutputStream(conn.getOutputStream());
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			out.flush();
			out.close();
			printResponse(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 使用HttpClient发送一个get方式的超链接请求.
	 * 
	 * @param urlpath
	 * @return
	 * @author shimiso
	 * @update 2012-6-29 上午11:58:14
	 */
	public static HttpResponse sendHttpGet(String urlpath) {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpGet httpget = new HttpGet(urlpath);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 20000); // 设置请求超时时间
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 20000); // 读取超时
			HttpResponse response = httpclient.execute(httpget);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 使用HttpClient发送一个post方式的请求.
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @author shimiso
	 * @update 2012-6-29 上午11:58:30
	 */
	public static HttpResponse sendHttpPost(String url,
			Map<String, String> params) {
		try {
			List<NameValuePair> param = new ArrayList<NameValuePair>(); // 参数
			if (params != null) {
				Iterator<Entry<String, String>> iterator = params.entrySet()
						.iterator();
				while (iterator.hasNext()) {
					Entry<String, String> entry = iterator.next();
					param.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
			}

			HttpPost request = new HttpPost(url);
			HttpEntity entity = new UrlEncodedFormEntity(param, "UTF-8");
			request.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 20000); // 设置请求超时时间
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					20000); // 读取超时
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 获取返回信息.
	 * 
	 * @param conn
	 * @author shimiso
	 * @update Feb 7, 2012 6:18:42 PM
	 */
	public static String printResponse(HttpURLConnection conn) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append("\n" + line);
			}
			System.out.println("==>" + sb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/** 
	 * 直接通过HTTP协议提交数据到服务器,实现表单提交功能 
	 * @param url 上传路径
	 * @param params 请求参数 key为参数名,value为参数值 
	 * @param files 上传文件
	 * @throws Exception 
	 */  
	public static String postFile(String url, Map<String, String> params, FormFile[] files) throws Exception {  
	    try {             
	        String BOUNDARY = "---------7d4a6d158c9"; //数据分隔线  
	        String MULTIPART_FORM_DATA = "multipart/form-data";  
	          
	        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();  
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
	        	 for (Entry<String, String> entry : params.entrySet()) {//构建表单字段内容
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
}
