/** A simple base64 encoding and decoding utility class
 * it can also encode and decode non ASII characters such as 
 * Chinese
 */

/**
 * This software is provided "AS IS," without a warranty of any kind.
 * anyone can use it for free,emails are welcomed concerning bugs or
 * suggestions.
 */

/**
 * Base64.java.  
 *
 * @version 1.0 06/19/2001
 * @author Wen Yu, yuwen_66@yahoo.com
 */
package com.aero.droid.dutyfree.talent.util.encode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class Base641 {
	//public static byte[] b1 = new byte[] { 12, 92, 73, 48, 96, 6, 77, 88 };

	private byte[] __asciiMap = new byte[] { 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '+', '/' };

	private byte __mapbyte2Ascii(byte b) {
		byte rs = 0;
		if (b >= 0 && b <= 63) {
			rs = __asciiMap[b];
		} else {
			throw new RuntimeException("Cannot map the character := " + b);
		}
		return rs;
	}

	private byte __mapAscii2byte(byte b) {
		byte rs = -1;
		for (int i = 0; i < __asciiMap.length; i++) {
			if (__asciiMap[i] == b) {
				rs = (byte) i;
				break;
			}
		}
		if (rs == -1) {
			throw new RuntimeException("Cannot map the character := " + b);
		}
		return rs;
	}
	//编码
	public  byte[] base64Encode(byte[] bytes) {
		byte[] result = null;
		if (bytes != null && bytes.length > 0) {
			try {
				int len = bytes.length;
				int i = 0;
				ByteArrayOutputStream outs = new ByteArrayOutputStream();
				byte[] data = new byte[4];
				while ((len - i) >= 3) {
					data[0] = __mapbyte2Ascii((byte) ((0xfc & bytes[i + 0]) >> 2));
					data[1] = __mapbyte2Ascii((byte) (((0xf0 & bytes[i + 1]) >> 4) | ((0x3 & bytes[i + 0]) << 4)));
					data[2] = __mapbyte2Ascii((byte) (((0xc0 & bytes[i + 2]) >> 6) | ((0xf & bytes[i + 1]) << 2)));
					data[3] = __mapbyte2Ascii((byte) (0x3f & bytes[i + 2]));
					outs.write(data);
					i += 3;
				}
				if (len - i == 2) {
					data[0] = __mapbyte2Ascii((byte) ((0xfc & bytes[i + 0]) >> 2));
					data[1] = __mapbyte2Ascii((byte) (((0xf0 & bytes[i + 1]) >> 4) | ((0x3 & bytes[i + 0]) << 4)));
					data[2] = __mapbyte2Ascii((byte) (0x0 | ((0xf & bytes[i + 1]) << 2)));
					data[3] = (byte) '=';
					outs.write(data);
				} else if (len - i == 1) {
					data[0] = __mapbyte2Ascii((byte) ((0xfc & bytes[i + 0]) >> 2));
					data[1] = __mapbyte2Ascii((byte) (0x0 | ((0x3 & bytes[i + 0]) << 4)));
					data[2] = (byte) '=';
					data[3] = (byte) '=';
					outs.write(data);
				}
				result = outs.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}
	
	//解码
	public byte[] base64Decode(byte[] bytes) {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		if (bytes != null) {
			try {
				if (bytes.length % 4 != 0) {
					return null;
				}
				int len = bytes.length;
				byte[] tmp = new byte[4];
				byte[] data = new byte[3];
				int i = 0;
				int j = 0;
				int k = 0;
				while (i < len) {
					for (j = 0; j < tmp.length; ++j) {
						tmp[j] = 0;
					}
					j = 0;
					k = 0;
					while (j < tmp.length && i < len) {
						if ('\n' == bytes[i]) {
							// omit.
						} else if ('\r' == bytes[i]) {
							// omit.
						} else if ('=' == bytes[i]) {
							tmp[j] = 0;
							++k; // k is the count of the placeholders. The
									// corresponding data won't be put into the
									// result.
						} else {
							tmp[j] = __mapAscii2byte(bytes[i]);
						}
						++j;
						++i;
					}
					if (j < tmp.length) {
						throw new RuntimeException(
								"Internal error. the j is not aligned well with 4. j = "
										+ j + ", i = " + i);
					}
					data[0] = (byte) (((0x3F & tmp[0]) << 2) | ((0x3F & tmp[1]) >> 4));
					data[1] = (byte) (((0x0F & tmp[1]) << 4) | ((0x3C & tmp[2]) >> 2));
					data[2] = (byte) (((0x03 & tmp[2]) << 6) | (0x3F & tmp[3]));
					for (int l = 0; l < (data.length - k); l++) {
						result.write(data[l]);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return result.toByteArray();
	}
	
	public static byte[] decode(String s) {
	       ByteArrayOutputStream bos = new ByteArrayOutputStream();
	       try {                                                                                                            
	           decode(s, bos);                                                                                               
	       } catch (IOException e) {
	           throw new RuntimeException();
	       }                                                                                                                 
	                                                                                                                         
	       byte[] decodedBytes = bos.toByteArray();                                                                            
	       try {                                                                                                            
	           bos.close();                                                                                                  
	           bos = null;                                                                                                   
	       } catch (IOException ex) {
	           System.err.println("Error while decoding BASE64: " + ex.toString());
	       }                                                                                                                  
	       return decodedBytes;                                                                                              
	   }    
	
	 private static void decode(String s, OutputStream os) throws IOException {
	       int i = 0;                                                                                                        
	       int len = s.length();                                                                                                      
	       while (true) {                                                                                                    
	           while (i < len && s.charAt(i) <= ' ')                                                                                                 
	                i++;                                                                                                        
	            if (i == len)                                                                                                
	                break;                                                                                                   
	            int tri = (decode(s.charAt(i)) << 18)                                                                         
	                    + (decode(s.charAt(i + 1)) << 12)                                                                     
	                    + (decode(s.charAt(i + 2)) << 6)                                                                     
	                    + (decode(s.charAt(i + 3)));                                                                                         
	            os.write((tri >> 16) & 255);                                                                                 
	            if (s.charAt(i + 2) == '=')                                                                                   
	                break;                                                                                                   
	            os.write((tri >> 8) & 255);                                                                                  
	            if (s.charAt(i + 3) == '=')                                                                                  
	                break;                                                                                                   
	            os.write(tri & 255);                                                                                                
	            i += 4;                                                                                                       
	        }                                                                                                                 
	    }
	 
	 private static int decode(char c) {                                                                                    
	       if (c >= 'A' && c <= 'Z')                                                                                         
	           return ((int) c) - 65;                                                                                         
	       else if (c >= 'a' && c <= 'z')                                                                                     
	           return ((int) c) - 97 + 26;                                                                                    
	       else if (c >= '0' && c <= '9')                                                                                    
	           return ((int) c) - 48 + 26 + 26;                                                                              
	       else                                                                                                              
	           switch (c) {                                                                                                   
	           case '+':                                                                                                      
	               return 62;                                                                                                 
	           case '/':                                                                                                     
	               return 63;                                                                                                
	           case '=':                                                                                                      
	               return 0;                                                                                                 
	           default:                                                                                                       
	               throw new RuntimeException("unexpected code: " + c);
	           }                                                                                                             
	   }     
}