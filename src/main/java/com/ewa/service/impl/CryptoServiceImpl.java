package com.ewa.service.impl;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.ewa.service.CryptoService;

public class CryptoServiceImpl implements CryptoService {
	public String encode(String text, String password) throws Exception {
        Key aesKey = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return encrypted.toString();
	}
	
	public String decode(String text, String password) throws Exception {
        Key aesKey = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(text.getBytes()));
        return decrypted;
	}
	
}
