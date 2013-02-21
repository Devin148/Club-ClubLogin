package com.dhurd.club.login.coreutils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CoreMath {
    private static final Logger logger = Logger.getLogger(CoreMath.class.getName());
    
    /**
     * Returns the MD5 sum of a given String.
     * @param toHash String to hash
     * @return hashed String
     */
    public static String getMD5(String toHash) {
        String md5String = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(toHash.getBytes());
            md5String = new String(md5Bytes);
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, "Failed to convert String to MD5", ex);
        }
        return md5String;
    }

}
