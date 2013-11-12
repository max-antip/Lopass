package com.lopass.test.crypto;

import com.lopass.crypto.Coder;
import com.lopass.file.Storage;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class KeyGeneratorTest {

    public static final String TEXT = "Using modes such as CFB and OFB, block ciphers can encrypt data in units smaller than the cipher's actual block size. When requesting such a mode, you may optionally specify the number of bits to be processed at a time by appending this number to the mode name as shown in the \"DES/CFB8/NoPadding\" and \"DES/OFB32/PKCS5Padding\" transformations. If no such number is specified, a provider-specific default is used. (For example, the SunJCE provider uses a default of 64 bits for DES.) Thus, block ciphers can be turned into byte-oriented stream ciphers by using an 8 bit mode such as CFB8 or OFB8.\n" +
            "Modes such as Authenticated Encryption with Associated Data (AEAD) provide authenticity assurances for both confidential data and Additional Associated Data (AAD) that is not encrypted. (Please see RFC 5116 for more information on AEAD and AEAD algorithms such as GCM/CCM.) Both confidential and AAD data can be used when calculating the authentication tag (similar to a Mac). This tag is appended to the ciphertext during encryption, and is verified on decryption.\n" +
            "\n" +
            "AEAD modes such as GCM/CCM perform all AAD authenticity calculations before starting the ciphertext authenticity calculations. To avoid implementations having to internally buffer ciphertext, all AAD data must be supplied to GCM/CCM implementations (via the updateAAD methods) before the ciphertext is processed (via the update and doFinal methods).";


    private Coder coder;
    Storage store;

    private static final String PASS = "maximus";
    private static final String DB = "max";
    private static final String PASS2 = "maximum";

    @Before
    public void initCoder() {
        coder = new Coder(PASS);
        store = new Storage(DB);
    }

    @Test
    public void testCoder() throws UnsupportedEncodingException, GeneralSecurityException {
//        byte[] encrypted = coder.encryptString(TEXT);
//        Assert.assertNotNull(encrypted);

//        wrightToDb(encrypted);

//        Key key2 = coder.getKey(PASS2);
        byte[] encrypted = readFromDB();
        String decryptedStr = coder.decryptString(encrypted);

        System.out.println(decryptedStr);
        Assert.assertNotNull(decryptedStr);
    }

    public void wrightToDb(byte[] data) {

        store.StoreToDB(data);
    }

    public byte[] readFromDB() {
        return store.loadDB();
    }


}
