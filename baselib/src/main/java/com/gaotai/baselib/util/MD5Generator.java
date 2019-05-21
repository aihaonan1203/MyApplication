package com.gaotai.baselib.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 * MD5码生成工具。
 * 
 * @author MengLiang
 */
public class MD5Generator {

	/**
	 * 将指定字符串进行MD5加密
	 * 
	 * @param param
	 *            需要加密的字符串
	 * @return MD5加密后的字符串
	 */
	public static String generateMD5(String param) {
		MessageDigest md5 = null;

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
		}
		char[] charArray = param.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();

		for (int j = 0; j < md5Bytes.length; j++) {
			int val = ((int) md5Bytes[j]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	
	 /**
     * 生成md5 16位
     * @param message
     * @return
     */
    public static String getMD5(String message) {
        String md5str = "";
        try {
            //1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            //2 将消息变成byte数组
            byte[] input = message.getBytes("UTF-8");
 
            //3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);
 
            //4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }
 
    /**
     * 二进制转十六进制
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
             digital = bytes[i];
 
            if(digital < 0) {
                digital += 256;
            }
            if(digital < 16){
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
    
    /**
	 * md5 加密 32位
	 * 
	 * @param passWord
	 * @return
	 */
	public static String encodePassWord(String passWord)
	{
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		md.update(passWord.getBytes());
		byte[] d = md.digest();
		String password = Base64Util.encode(d);
		return password;
	}
	
	/*
	private static final char HEX_DIG[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
	};
	
	public static String getMD5code(String str) throws Exception{
		MessageDigest md5=MessageDigest.getInstance("MD5");
		byte[] resource=str.getBytes();
		byte[] result=md5.digest(resource);
		return getHexString(result);
	}
	
	static String getHexString(byte[] b){
		StringBuilder sb = new StringBuilder();
		for(int i= 0; i<b.length;i++){
			 sb.append( HEX_DIG[b[i]& 0xf0 >>>4]);
			 sb.append( HEX_DIG[b[i]& 0x0f]);
		}
		return sb.toString();
	}*/


	public static final String IV = "njgtkjgs20031209";

	// 加密
	public static String Encrypt(String sSrc, String sKey) throws Exception
	{
		if(sKey == null)
		{
			System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if(sKey.length() != 16)
		{
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("UTF-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));

		//return new Base64Util.encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。

		String password = Base64Util.encode(encrypted);
		return password;
	}

	// 解密
	public static String Decrypt(String sSrc, String sKey) throws Exception
	{
		try
		{
			// 判断Key是否正确
			if(sKey == null)
			{
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if(sKey.length() != 16)
			{
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("UTF-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			//byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密
			byte[] encrypted1 =Base64Util.decode(sSrc);
			try
			{
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			}
			catch (Exception e)
			{
				System.out.println(e.toString());
				return null;
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.toString());
			return null;
		}
	}
	public static String Encrypt(String cSrc) throws Exception
	{
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 * 此处使用AES-128-CBC加密模式，key需要为16位。
		 */
		String cKey = "1827593047681295";
		// 需要加密的字串
		//String cSrc = "12345a";
		//System.out.println(cSrc);
		// 加密
		//long lStart = System.currentTimeMillis();
		String enString = MD5Generator.Encrypt(cSrc, cKey);
		return enString;
	}



}
