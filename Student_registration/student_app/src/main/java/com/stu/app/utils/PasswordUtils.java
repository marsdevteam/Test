package com.stu.app.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.jboss.logging.Logger;

/**
 * The PasswordUtils class provides methods for encrypting and decrypting passwords using the Blowfish algorithm.
 */
public class PasswordUtils {

	private static final Logger log = Logger.getLogger(PasswordUtils.class);

	private static final String KEY = "]Y7kS!btB+";
	private static final byte[] KEYDATA = KEY.getBytes();
	private static final String ALGORITHM = "Blowfish";

	  /**
     * Encrypts a password or phrase using the Blowfish algorithm.
     *
     * @param phrase The input password or phrase to be encrypted.
     * @return The encrypted password as a Base64-encoded string, or null if an error occurs.
     */
	public static String encrypt(String phrase) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(KEYDATA, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] tmp = cipher.doFinal(phrase.getBytes());
			return new String(Base64.getEncoder().encode(tmp));

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	  /**
     * Decrypts an encrypted password or phrase using the Blowfish algorithm.
     *
     * @param phrase The Base64-encoded encrypted password or phrase to be decrypted.
     * @return The decrypted password or phrase, or null if an error occurs.
     */
	public static String decrypt(String phrase) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(KEYDATA, ALGORITHM);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] tmp = cipher.doFinal(Base64.getDecoder().decode(phrase));
			return new String(tmp);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(decrypt("5wem2qXsB8g="));
		
	}
}
