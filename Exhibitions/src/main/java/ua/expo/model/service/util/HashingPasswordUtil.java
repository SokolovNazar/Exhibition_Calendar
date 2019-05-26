package ua.expo.model.service.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Util class which  give instruments for
 * password hashing.
 * @author andrii
 */
public class HashingPasswordUtil {

    public String encryptionSHA256(String purpose){
        return encrypt(purpose, "SHA-256");
    }

    public boolean isEqualsSHA256(String simple, String hashed){
        if(Utils.isNotNull(simple, hashed)) {
            return encryptionSHA256(simple).equals(hashed);
        }
        return false;
    }

    private String encrypt(String purpose, String hash){
        try {
            MessageDigest md = MessageDigest.getInstance(hash);
            md.update(purpose.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return purpose;
    }
}
