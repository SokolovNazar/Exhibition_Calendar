package ua.expo.modelTests.serviceTests.UtilsTests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.expo.model.service.util.HashingPasswordUtil;

public class HashingTest {
    private static HashingPasswordUtil hashingUtil;

    @BeforeClass
    public static void init() {
        hashingUtil = new HashingPasswordUtil();
    }

    @Test
    public void encryptingSHA256Test() {
        String testWord = "hello";
        String hashWord = hashingUtil.encryptionSHA256(testWord);
        Assert.assertEquals(hashWord, hashingUtil.encryptionSHA256(testWord));
    }

    @Test
    public void wrongEncryptingSHA256Test() {
        String testWord = "hello";
        String wrongWord = "hello1";
        String hashWord = hashingUtil.encryptionSHA256(testWord);
        Assert.assertNotEquals(hashWord, hashingUtil.encryptionSHA256(wrongWord));
    }

    @Test
    public void isEqualsSHA256Test() {
        String testWord = "hello";
        String hashWord = hashingUtil.encryptionSHA256(testWord);
        Assert.assertTrue(hashingUtil.isEqualsSHA256(testWord, hashWord));
    }

    @Test
    public void isNowEqualsSHA256Test() {
        String testWord = "hello";
        String wrongWord = "bye";
        String hashWord = hashingUtil.encryptionSHA256(testWord);
        Assert.assertFalse(hashingUtil.isEqualsSHA256(wrongWord, hashWord));
    }
}
