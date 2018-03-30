package com.smartin.timedic.caregiver.tools;

import android.util.Base64;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import  com.smartin.timedic.caregiver.config.Constants;

public class AesUtilHelper {
    private final int keySize;
    private final int iterationCount;
    private final Cipher cipher;
    
    public AesUtilHelper(int keySize, int iterationCount) {
        this.keySize = keySize;
        this.iterationCount = iterationCount;
        try {
            cipher = Cipher.getInstance(Constants.TRANSFORM);
        }
        catch (Exception e) {
            throw fail(e);
        }
    }
    
    public String encrypt(String salt, String iv, String passphrase, String plaintext) {
        try {
            SecretKey key = generateKey(salt, passphrase);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plaintext.getBytes(Constants.CHARSET_ENC));
            return base64(encrypted);
        }
        catch (UnsupportedEncodingException e) {
            throw fail(e);
        }
    }
    
    public String decrypt(String salt, String iv, String passphrase, String ciphertext) {
        try {
            SecretKey key = generateKey(salt, passphrase);
            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, base64(ciphertext));
            return new String(decrypted, Constants.CHARSET_ENC);
        }
        catch (UnsupportedEncodingException e) {
            throw fail(e);
        }
    }
    
    private byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) {
        try {
            cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
            return cipher.doFinal(bytes);
        }
        catch (Exception e) {
        	e.printStackTrace();
            throw fail(e);
        }
    }
    
    private SecretKey generateKey(String salt, String passphrase) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(Constants.SECRET_KEY_FACTORY);
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), iterationCount, keySize);
            SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), Constants.SECURITY_ALGO);
            return key;
        }
        catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }
    
    public static String random(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);
        return hex(salt);
    }
    
    public static String base64(byte[] bytes) {
        String dataEncoded = Base64.encodeToString(bytes, Base64.DEFAULT);
        return dataEncoded;
    }
    
    public static byte[] base64(String str) {
        byte[] dataEncoded = Base64.decode(str, Base64.DEFAULT);
        return dataEncoded;
    }
    
    public static String hex(byte[] bytes) {
        return Hex.encodeHexString( bytes );
    }
    
    public static byte[] hex(String str) throws DecoderException {
        return Hex.decodeHex(str.toCharArray());
    }
    
    private IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }
}
