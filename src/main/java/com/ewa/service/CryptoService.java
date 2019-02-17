package com.ewa.service;

/**
 * Service interface for crypto functions
 * @author fbertos
 *
 */
public interface CryptoService {
	/**
	 * Encode a text by using a security word
	 * @param text Text to encode
	 * @param password Security word
	 * @return Encoded text
	 * @throws Exception If any error
	 */
	public String encode(String text, String password) throws Exception;
	
	/**
	 * Decode a text that was encoded with a security word
	 * @param text Text to decode
	 * @param password Security word
	 * @return Decoded text
	 * @throws Exception If any error
	 */
	public String decode(String text, String password) throws Exception;
	
	/**
	 * Base64 encode
	 * @param text Text bytes to encode
	 * @return Text in base64
	 * @throws Exception If any error
	 */
	public String encodeBase64(byte[] text) throws Exception;
}
