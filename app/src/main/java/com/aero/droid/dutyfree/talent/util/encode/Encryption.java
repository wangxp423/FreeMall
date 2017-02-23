package com.aero.droid.dutyfree.talent.util.encode;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

	/**
	 * 加密
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] getFromBASE64(String s) {

		byte c[] = Base641.decode(s);
		return c;

	}

	public static String bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] getFromBASE64OLD(String s) {
		byte[] b = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				return b;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(String str) throws NoSuchAlgorithmException {
		return Encryption.encrypt(str, "MD5");
	}

	/**
	 * @param str
	 * @param encrytStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static String encrypt(String str, String encrytStr)
			throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance(encrytStr);
		byte[] bytes = null;
		try {
			bytes = md5.digest(str.getBytes("utf8"));
			StringBuilder ret = new StringBuilder(bytes.length << 1);
			for (int i = 0; i < bytes.length; i++) {
				ret.append(Character.forDigit((bytes[i] >> 4) & 0xf, 16));
				ret.append(Character.forDigit(bytes[i] & 0xf, 16));
			}
			return ret.toString().toUpperCase();
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String getStringFormBase64(String s) {
		String back_string = "";

		try {
			byte[] get_string = new Cipher(
					"aero.com.cn created by aero.com.cn")
					.decrypt(getFromBASE64(s));
			back_string = new String(get_string);
			// {"result":{"count":0}}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return back_string;
	}

	/**
	 * @param b
	 * @return
	 */
	public static String getBASE64(byte[] b) {
		String s = null;
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	public static String getFromFileBASE64(File file) {

		return getStringFormBase64(getFileToString(file));

	}

	public static String getFileToString(File file) {
		InputStream in = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			in = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in));
			String str;
			while ((str = br.readLine()) != null) {
				str.replaceAll(" ", "");
				sb.append(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (br != null) {
					br.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return sb.toString();

	}

	/**
	 * 对字节数据进行Base16编码。
	 * 
	 * @param src
	 *            源字节数组
	 * @return 编码后的字符串
	 */
	public static String encode(byte src[]) throws Exception {
		StringBuffer strbuf = new StringBuffer(src.length * 2);
		int i;

		for (i = 0; i < src.length; i++) {
			if (((int) src[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) src[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	/**
	 * 对Base16编码的字符串进行解码。
	 * 
	 *            源字串
	 * @return 解码后的字节数组
	 */
	public static byte[] decode(String hexString) throws Exception {
		byte[] bts = new byte[hexString.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(
					hexString.substring(2 * i, 2 * i + 2), 16);
		}
		return bts;
	}

}