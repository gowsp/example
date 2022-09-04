package com.toss.controller;

import javax.crypto.Cipher;
import java.security.*;

public class EncryptUtil {
    /**
     * 生成RSA密钥对
     */
    public static KeyPair generateKeyPair(int keySize) {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(keySize);
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RAS加密
     */
    public static byte[] encryptByRSA(byte[] input, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipher.update(input);
            return cipher.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * RSA解密
     */
    public static String decryptByRSA(byte[] cipherText, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decipheredText = cipher.doFinal(cipherText);
            return new String(decipheredText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
