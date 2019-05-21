package com.gaotai.baselib.http;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *<b>帮助类:Http协议请求(Post/Get)的各种接口</b>
 */
public class SocketHttpRequester {

	public static byte[] postXml(String path, String xml, String encoding) throws Exception{
		byte[] data = xml.getBytes(encoding);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml; charset="+ encoding);
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setConnectTimeout(5 * 1000);
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if(conn.getResponseCode()==200){
			return readStream(conn.getInputStream());
		}
		return null;
	}
	
	
	public static boolean post(String path, Map<String, String> params, FormFile[] files) throws Exception{     
        final String BOUNDARY = "---------------------------7da2137580612"; 
        final String endline = "--" + BOUNDARY + "--\r\n";
        
        int fileDataLength = 0;
        for(FormFile uploadFile : files){
        	StringBuilder fileExplain = new StringBuilder();
 	        fileExplain.append("--");
 	        fileExplain.append(BOUNDARY);
 	        fileExplain.append("\r\n");
 	        fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
 	        fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
 	        fileExplain.append("\r\n");
 	        fileDataLength += fileExplain.length();
        	if(uploadFile.getInStream()!=null){
        		fileDataLength += uploadFile.getFile().length();
	 	    }else{
	 	    	fileDataLength += uploadFile.getData().length;
	 	    }
        }
        StringBuilder textEntity = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            textEntity.append("--");
            textEntity.append(BOUNDARY);
            textEntity.append("\r\n");
            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
            textEntity.append(entry.getValue());
            textEntity.append("\r\n");
        }
        
        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;
        
        URL url = new URL(path);
        int port = url.getPort()==-1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);	  
        socket.setSoTimeout(30 * 1000);
        OutputStream outStream = socket.getOutputStream();
        String requestmethod = "POST "+ url.getPath()+" HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, video/mp4,*/*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: "+ dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
        outStream.write(host.getBytes());
        outStream.write("\r\n".getBytes());
        outStream.write(textEntity.toString().getBytes());	  
        for(FormFile uploadFile : files){
        	StringBuilder fileEntity = new StringBuilder();
 	        fileEntity.append("--");
 	        fileEntity.append(BOUNDARY);
 	        fileEntity.append("\r\n");
 	        fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
 	        fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
 	        outStream.write(fileEntity.toString().getBytes());
 	        if(uploadFile.getInStream()!=null){
 	        	byte[] buffer = new byte[1024];
 	        	int len = 0;
 	        	while((len = uploadFile.getInStream().read(buffer, 0, 1024))!=-1){
 	        		outStream.write(buffer, 0, len);
 	        	}
 	        	uploadFile.getInStream().close();
 	        }else{
 	        	outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
 	        }
 	        outStream.write("\r\n".getBytes());
        }
        outStream.write(endline.getBytes());
        
        String readLine = "";
        StringBuffer sb=new StringBuffer();
        String str="-1";
        try {
        	/**
			 * 获取响应码 200=成功 当响应成功，获取响应的流
			 */
			DataInputStream inputStream = new DataInputStream(
					socket.getInputStream());
			byte[] buffer = new byte[1024];
			inputStream.read(buffer, 0, 1024);
			str = new String(buffer, "UTF-8");
			str = str.trim();
			//MyLog.i("str="+str);
			if(!str.equals("")&&str.indexOf("200")!=-1){
				str=str.substring(str.length()-1);
				if(str.equals("0")){
					return true;
				}
			}
		} catch (Exception e) {
			//MyLog.i(e.toString());
			e.printStackTrace();
		}finally{
			//MyLog.i("关闭方法");
			 outStream.flush();
		     outStream.close();
		     socket.close();
		}
        return false;
	}
	
	public static String postFile(String path, Map<String, String> params, FormFile file) throws Exception{
		if(file ==null)
		{
			FormFile[] files = null;
			return postFile(path, params, files);
		}
		return postFile(path, params, new FormFile[]{file});
	}
		
	public static String postFile(String path, Map<String, String> params, FormFile[] files) throws Exception{     
        final String BOUNDARY = "---------------------------7da2137580612"; 
        final String endline = "--" + BOUNDARY + "--\r\n";
        
        int fileDataLength = 0;
	    if(files != null)
	    {
	        for(FormFile uploadFile : files){
	        	StringBuilder fileExplain = new StringBuilder();
	 	        fileExplain.append("--");
	 	        fileExplain.append(BOUNDARY);
	 	        fileExplain.append("\r\n");
	 	        fileExplain.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
	 	        fileExplain.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
	 	        fileExplain.append("\r\n");
	 	        fileDataLength += fileExplain.length();
	        	if(uploadFile.getInStream()!=null){
	        		fileDataLength += uploadFile.getFile().length();
		 	    }else{
		 	    	fileDataLength += uploadFile.getData().length;
		 	    }
	        }
	    }
        StringBuilder textEntity = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            textEntity.append("--");
            textEntity.append(BOUNDARY);
            textEntity.append("\r\n");
            textEntity.append("Content-Disposition: form-data; name=\""+ entry.getKey() + "\"\r\n\r\n");
            textEntity.append(entry.getValue());
            textEntity.append("\r\n");
        }
        
        int dataLength = textEntity.toString().getBytes().length + fileDataLength +  endline.getBytes().length;
        
        URL url = new URL(path);
        int port = url.getPort()==-1 ? 80 : url.getPort();
        Socket socket = new Socket(InetAddress.getByName(url.getHost()), port);	       
        OutputStream outStream = socket.getOutputStream();
        String strurl = url.getPath();
        int pathparm = path.indexOf("?");
        if(pathparm >-1)
        {
        	//strurl = "/zhxy-mobile/client/oaEquipmentRepair/save?access_token=7b5988ef-b135-4ce6-9f23-b39f367f77ae";
        	strurl = strurl + path.substring(pathparm);
        }
        String requestmethod = "POST " + strurl +" HTTP/1.1\r\n";
        outStream.write(requestmethod.getBytes());
        String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, video/mp4,*/*\r\n";
        outStream.write(accept.getBytes());
        String language = "Accept-Language: zh-CN\r\n";
        outStream.write(language.getBytes());
        String contenttype = "Content-Type: multipart/form-data; boundary="+ BOUNDARY+ "\r\n";
        outStream.write(contenttype.getBytes());
        String contentlength = "Content-Length: "+ dataLength + "\r\n";
        outStream.write(contentlength.getBytes());
        String alive = "Connection: Keep-Alive\r\n";
        outStream.write(alive.getBytes());
        String host = "Host: "+ url.getHost() +":"+ port +"\r\n";
        outStream.write(host.getBytes());
        outStream.write("\r\n".getBytes());
        outStream.write(textEntity.toString().getBytes());	  
        if(files != null)
	    {
	        for(FormFile uploadFile : files){
	        	StringBuilder fileEntity = new StringBuilder();
	 	        fileEntity.append("--");
	 	        fileEntity.append(BOUNDARY);
	 	        fileEntity.append("\r\n");
	 	        fileEntity.append("Content-Disposition: form-data;name=\""+ uploadFile.getParameterName()+"\";filename=\""+ uploadFile.getFilname() + "\"\r\n");
	 	        fileEntity.append("Content-Type: "+ uploadFile.getContentType()+"\r\n\r\n");
	 	        byte [] bytes = fileEntity.toString().getBytes();
				outStream.write(bytes);
	 	        if(uploadFile.getInStream()!=null){
	 	        	byte[] buffer = new byte[1024];
	 	        	int len = 0;
	 	        	while((len = uploadFile.getInStream().read(buffer, 0, 1024))!=-1){
	 	        		outStream.write(buffer, 0, len);
	 	        	}
	 	        	uploadFile.getInStream().close();
	 	        }else{
	 	        	outStream.write(uploadFile.getData(), 0, uploadFile.getData().length);
	 	        }
	 	        outStream.write("\r\n".getBytes());
	        }
	    }
        outStream.write(endline.getBytes());
        BufferedReader httpResponse;
        String readLine = "";
        StringBuffer sb=new StringBuffer();
        String str="-1";
        try {
        	/**
			 * 获取响应码 200=成功 当响应成功，获取响应的流
			 */
			DataInputStream inputStream = new DataInputStream(
					socket.getInputStream());
			
			byte[] buffer = new byte[1024*2]; 
			inputStream.read(buffer, 0, 1024*2);
			str = new String(buffer, "UTF-8");
			str = str.trim();
						
			//MyLog.i("str="+str);			
			if(!str.equals("")&&str.indexOf("200")!=-1){
				String resulst = "";
				httpResponse = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK")); 
	            // 读取每一行的数据.注意大部分端口操作都需要交互数据。 
	            String lineStr = ""; 
	            while ((lineStr = httpResponse.readLine()) != null) {
	            	if(lineStr.indexOf("resultCode") >-1)
	            	{
	            		resulst = lineStr;
	            	}
	                //System.out.println(lineStr); 
	            }

	            if(resulst.equals(""))
	            {
	            	String [] strinfo = str.split("\\n");
	            	for(int i=0;i<strinfo.length;i++)
	            	{
	            		if(strinfo[i].indexOf("resultCode") >-1)
		            	{
		            		resulst = strinfo[i];
		            	}
	            	}
	            }
	            //httpResponse.close();
				return resulst;				
			}
		} catch (Exception e) {
			//MyLog.i(e.toString());
			e.printStackTrace();
		}finally{
			//MyLog.i("关闭方法");
			 outStream.flush();
		     outStream.close();		     
		     socket.close();
		}
        return "";
	}
	
	public static boolean post(String path, Map<String, String> params, FormFile file) throws Exception{
	   return post(path, params, new FormFile[]{file});
	}
	

	
	public static byte[] post(String path, Map<String, String> params, String encode) throws Exception{
		StringBuilder parambuilder = new StringBuilder("");
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				parambuilder.append(entry.getKey()).append("=")
					.append(URLEncoder.encode(entry.getValue(), encode)).append("&");
			}
			parambuilder.deleteCharAt(parambuilder.length()-1);
		}
		byte[] data = parambuilder.toString().getBytes();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword,video/mp4,*/*");
		conn.setRequestProperty("Accept-Language", "zh-CN");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setRequestProperty("Connection", "Keep-Alive");
		
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(data);
		outStream.flush();
		outStream.close();
		if(conn.getResponseCode()==200){
			
			return readStream(conn.getInputStream());
		}
		return null;
	}
	
	/**
	 * 将输入流转换成字节数组
	 */
	public static byte[] readStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream(); 
		byte[] buffer = new byte[1024];
		int len = -1;
		while( (len=inStream.read(buffer)) != -1){
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
	/*
	 * 使用方法例子：
	 * Map<String, String> req = new HashMap<String, String>();
		//req.putAll(getRequestBaseMap());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());
		req.put("transactionId", dateString);
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		req.put("timestamp", formatter1.format(new Date()));
byte[] returnstr=SocketHttpRequester.post(GlobalVariables.yixinbjpath, req, "utf-8");
			if(returnstr!=null){
				String result=new String(returnstr);
*/
	
}

