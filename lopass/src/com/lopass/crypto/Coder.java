package com.lopass.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Coder {

    //    private static final String PART_1 = "Lopass programm";
//    private static final String PART_2 = "Maximus Antipius";
    private Cipher cipher;

    Key key;

    public Coder(String pass) {
        try {
            key = getKey(pass);
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    public byte[] encryptString(String data) throws GeneralSecurityException {
        byte[] encryptedData = null;
        try {
            encryptedData = encryptData(data.getBytes("UTF-8"), key);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encryptedData;
    }

    public String decryptString(byte[] data) throws GeneralSecurityException {
        String decryptedStr = null;
        try {
            byte[] decryptedData = decryptData(data, key);
            decryptedStr = new String(decryptedData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decryptedStr;
    }

    public Key getKey(String pass) {
        SecretKeySpec secretKeySpec = null;
        try {
            byte[] key = (pass).getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");

            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);

            secretKeySpec = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return secretKeySpec;
    }

    public byte[] encryptData(byte[] data, Key key) throws GeneralSecurityException {
        byte[] encryptedData = null;
        cipher.init(Cipher.ENCRYPT_MODE, key);
        encryptedData = cipher.doFinal(data);

        return encryptedData;
    }

    public byte[] decryptData(byte[] data, Key key) throws GeneralSecurityException{
        byte[] decryptData = null;

        cipher.init(Cipher.DECRYPT_MODE, key);
        decryptData = cipher.doFinal(data);

        return decryptData;
    }


}
