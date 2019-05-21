package com.gaotai.baselib.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

/**
 * 
 * <p>
 * 提供通过Http协议上传的各种接口。如上传字符串、上传文件+参数等
 * </p>
 * 
 * @version 1.0
 * @create 2011-7-1
 */
public class HttpUpload {
	public static final String NO_RESPONSE = "no";

	/**
	 * 
	 * <p>
	 * 上传字符串到服务器
	 * </p>
	 * 
	 * @param urlStr
	 *            上传的服务器接口地址
	 * @param data
	 *            上传的字符串
	 * @param encode
	 *            返回数据的编码格式
	 * @return 服务器返回的字符串内容
	 */
	public static String upload(String urlStr, String data, String encode)
			throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		setConnectParam(connection, String.valueOf(data.length()));
		return upload(connection, data.getBytes(), encode);
	}

	/**
	 * 
	 * <p>
	 * 上传二进制数据，主要用在需要上传图片等大数据量二进制文件的场合
	 * </p>
	 * 
	 * @param urlStr
	 *            上传的服务器接口地址
	 * @param data
	 *            上传的数据buffer
	 * @param encode
	 *            返回数据的编码格式
	 * @return 服务器返回的字符串内容
	 */
	public static String upload(String urlStr, byte[] data, String encode)
			throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		setConnectParam(connection, String.valueOf(data.length));
		return upload(connection, data, encode);
	}

	private static void setConnectParam(HttpURLConnection connection,
			String length) {
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Cache-Control", "no-cache");
		connection.setRequestProperty("Accept", "*/*");
		connection.setRequestProperty("Content-Type", "text/html");
		connection.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
		connection.setRequestProperty("Accept-Language", "zh-cn");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
		connection.setRequestProperty("Content-Length", length);
		connection.setConnectTimeout(5000);
		connection.setDoOutput(true);
	}

	private static String upload(HttpURLConnection connection, byte[] data,
			String encode) throws Exception {
		InputStream in = null;
		java.io.BufferedReader breader = null;
		try {
			connection.connect();
			connection.getOutputStream().write(data);
			System.out.println(connection.getResponseCode());
			if (connection.getResponseCode() == 200) {
				in = connection.getInputStream();
				breader = new BufferedReader(new InputStreamReader(in, encode));
				String str = breader.readLine();
				StringBuffer sb = new StringBuffer();
				while (str != null) {
					sb.append(str);
					str = breader.readLine();
				}
				return sb.toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (null != breader) {
				breader.close();
			}
			if (null != in) {
				in.close();
			}
		}
		return "";
	}
	/*
	private static String post(HttpPost postRequest) throws Exception {
		org.apache.http.client.HttpClient httpClient = new DefaultHttpClient();
		HttpResponse res;
		try {
			res = httpClient.execute(postRequest);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		if (res != null
				&& res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream instream = res.getEntity().getContent();
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				return reader.toString();

			} catch (RuntimeException ex) {

				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection and release it back to the connection manager.
				postRequest.abort();
				throw ex;

			} finally {
				// Closing the input stream will trigger connection release
				instream.close();
			}
		}

		// When HttpClient instance is no longer needed,
		// shut down the connection manager to ensure
		// immediate deallocation of all system resources
		httpClient.getConnectionManager().shutdown();
		return "no";
	}*/

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url, HashMap<String, String> params) {
		String result = "";
		BufferedReader in = null;
		try {
			StringBuilder parambuilder = new StringBuilder("");
			if(params!=null && !params.isEmpty()){
				for(Map.Entry<String, String> entry : params.entrySet()){
					parambuilder.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), "utf-8")).append("&");
				}
				parambuilder.deleteCharAt(parambuilder.length()-1);
			}
			String urlName = url;
			if(!TextUtils.isEmpty(parambuilder.toString()))
			{	
				if(url.indexOf("?") == -1)
				{
					urlName = url + "?" + parambuilder.toString();
				}
				else
				{

					urlName = url + "&" + parambuilder.toString();
				}
			}
			
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}


}
