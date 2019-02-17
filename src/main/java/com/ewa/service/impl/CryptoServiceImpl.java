package com.ewa.service.impl;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bson.internal.Base64;
import org.springframework.stereotype.Service;

import com.ewa.service.CryptoService;

/**
 * Service implementation for crypto functions
 * @author fbertos
 *
 */
@Service
public class CryptoServiceImpl implements CryptoService {
	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CryptoService#encode(java.lang.String, java.lang.String)
	 */
	public String encode(String text, String password) throws Exception {
        Key aesKey = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return Base64.encode(encrypted);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CryptoService#decode(java.lang.String, java.lang.String)
	 */
	public String decode(String text, String password) throws Exception {
        Key aesKey = new SecretKeySpec(Base64.decode(password), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(text.getBytes()));
        return decrypted;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ewa.service.CryptoService#encodeBase64(byte[])
	 */
	public String encodeBase64(byte[] text) throws Exception {
		return Base64.encode(text);
	}
}
