package com.ewa.service;

/**
 * Service interface for crypto
 * @author fbertos
 *
 */
public interface CryptoService {
	public String encode(String text, String password) throws Exception;
	public String decode(String text, String password) throws Exception;
	public String encodeBase64(byte[] text) throws Exception;
}
