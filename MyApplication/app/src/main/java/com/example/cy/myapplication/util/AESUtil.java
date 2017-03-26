package com.example.cy.myapplication.util;

import android.util.Base64;
import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by cy on 2017/3/26.
 */

public class AESUtil {

    /**
     * AES加密
     * @param key 加密种子（用于产生密钥字节数组）
     * @param passWord 要加密的明文密码
     * @return
     * @throws Exception
     */
    public static String encrypt(String key, String passWord) throws Exception {
        return toBase64String(encrypt(getRawKey(key.getBytes()),passWord));
    }

    /**
     * AES解密
     * @param key 加密种子（用于产生密钥字节数组）
     * @param encryptedPassWord 密码被AES加密后的密文
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String encryptedPassWord) throws Exception {
        return new String(decrypt(getRawKey(key.getBytes()),toBase64Bytes(encryptedPassWord)));
    }



    /**
     * @param rawKey   密钥
     * @param clearPwd 明文字符串
     * @return 密文字节数组
     */
    private static byte[] encrypt(byte[] rawKey, String clearPwd) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encypted = cipher.doFinal(clearPwd.getBytes());
            return encypted;
        } catch (Exception e) {
            Log.e("encrypt错误","encrypt错误");
            return null;
        }
    }

    /**
     * @param encrypted 密文字节数组
     * @param rawKey    密钥
     * @return 解密后的字符串
     */
    private static String decrypt(byte[] rawKey, byte[] encrypted) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(rawKey, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            Log.e("decrypt错误","decrypt错误");
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param seed 种子数据
     * @return 密钥数据
     */
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
        SecureRandom sr = null;
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(seed);
        kgen.init(128, sr); //256 bits or 128 bits,192bits
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;

    }

    private static String toBase64String(byte[] bytes){
        return new String(Base64.encode(bytes, Base64.DEFAULT));
    }

    private static byte[] toBase64Bytes(String string){
        return Base64.decode(string, Base64.DEFAULT);
    }
}
