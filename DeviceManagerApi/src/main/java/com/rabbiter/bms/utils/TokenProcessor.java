package com.rabbiter.bms.utils;
/**
 * 生成Token的工具类：  
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import java.util.Base64;

/**
 * 生成Token的工具类  
 *
 */
public class TokenProcessor {

    private TokenProcessor(){};
    private static final TokenProcessor instance = new TokenProcessor();

    public static TokenProcessor getInstance() {
        return instance;
    }

    /**
     * 生成Token  
     * @return
     */
    public String makeToken() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte md5[] =  md.digest(token.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(md5);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block  
            e.printStackTrace();
        }
        return null;
    }
}