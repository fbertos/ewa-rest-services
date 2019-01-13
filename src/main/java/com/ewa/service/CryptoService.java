package com.ewa.service;

public interface CryptoService {
	public String encode(String text, String password) throws Exception;
	public String decode(String text, String password) throws Exception;
}
