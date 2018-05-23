package com.cwc.web.ypzj.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptologyUtil {
	/*
	@param bytes:待编码的byte[]
	 */
	public static String base64Encode(byte[] bytes){
		return new BASE64Encoder().encode(bytes);
	}

	/*
	@param base64Code:待解码的base64 code
	 */
	public static byte[] base64Decode(String base64Code) throws Exception{
		return base64Code==null||base64Code.length()==0 ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}
	/*
	@param content:待加密的内容
	@param encryptKey:加密密钥
	@return 加密后的byte[]
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(encryptKey.getBytes(StandardCharsets.UTF_8));
		kgen.init(128, random);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, kgen.generateKey());
		return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
	}

	/*
	对aesEncryptToBytes的返回结果byte[]用base64编码成string
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
		return base64Encode(aesEncryptToBytes(content, encryptKey));
	}

	/*
	@param encryptBytes:待解密的内容的byte[]形式
	@param decryptKey:解密密钥
	@return 返回解密后的内容
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(decryptKey.getBytes(StandardCharsets.UTF_8));
		kgen.init(128, random);

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, kgen.generateKey());
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return new String(decryptBytes,StandardCharsets.UTF_8);
	}

	/*
	@param encryptStr:使用base64编码后的AES加密内容
	@param AES解密密钥
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
		return encryptStr==null||encryptStr.length()==0 ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
	}
}
