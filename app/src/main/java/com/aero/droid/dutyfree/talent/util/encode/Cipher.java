package com.aero.droid.dutyfree.talent.util.encode;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Cipher {

	private final String password;
	  
    public Cipher(String password) {
        this.password = password;  
    }  
  
    public byte[] encrypt(byte[] plainText) throws Exception {
        return transform(true, plainText);  
    }  
  
    public byte[] decrypt(byte[] cipherText) throws Exception {
    	
        return transform(false, cipherText);  
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

	public static String hexStr2Str(String hexStr)
	  {    
	        String str = "0123456789ABCDEF";
	      char[] hexs = hexStr.toCharArray();    
	        byte[] bytes = new byte[hexStr.length() / 2];    
	        int n;    
	  
	       for (int i = 0; i < bytes.length; i++)  
	        {    
	            n = str.indexOf(hexs[2 * i]) * 16;    
	           n += str.indexOf(hexs[2 * i + 1]);    
            bytes[i] = (byte) (n & 0xff);    
	       }    
	        return new String(bytes);
	   } 

    private byte[] transform(boolean encrypt, byte[] inputBytes) throws Exception {
        byte[] key = DigestUtils.md5(password.getBytes("UTF-8"));  
  
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESFastEngine()));  
        cipher.init(encrypt, new KeyParameter(key));  
  
        ByteArrayInputStream input = new ByteArrayInputStream(inputBytes);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
  
        int inputLen;  
        int outputLen;  
  
        byte[] inputBuffer = new byte[1024];  
        byte[] outputBuffer = new byte[cipher.getOutputSize(inputBuffer.length)];  
  
        while ((inputLen = input.read(inputBuffer)) > -1) {  
            outputLen = cipher.processBytes(inputBuffer, 0, inputLen, outputBuffer, 0);  
            if (outputLen > 0) {  
                output.write(outputBuffer, 0, outputLen);  
            }  
        }  
  
        outputLen = cipher.doFinal(outputBuffer, 0);  
        if (outputLen > 0) {  
            output.write(outputBuffer, 0, outputLen);  
        }  
  
        return output.toByteArray();  
    }
}
