package com.cwc.web.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static MessageDigest getInstance() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}
}
