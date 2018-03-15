package com.cwc.web.ypzj.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static MessageDigest getInstance() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("MD5");
	}
}
