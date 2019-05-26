package ua.expo.modelTests.serviceTests.UtilsTests;

import org.junit.Assert;
import org.junit.Test;
import ua.expo.model.service.util.Utils;

public class UtilsTest {
    @Test
    public void notNullTest(){
        Assert.assertTrue(Utils.isNotNull("one","" , "two", "end"));
    }

    @Test
    public void notNullEmptyTest(){
        Assert.assertTrue(Utils.isNotNull());
    }

    @Test
    public void notNullFalseTest(){
        Assert.assertFalse(Utils.isNotNull("", "empty", "null", null));
    }

    @Test
    public void isNumberTest(){
        Assert.assertTrue(Utils.isNumber("135"));
    }

    @Test
    public  void isNumberNegativeTest(){
        Assert.assertTrue(Utils.isNumber("-4"));
    }

    @Test
    public void isNumberDoubleTest(){
        Assert.assertFalse(Utils.isNumber("5.4"));
        Assert.assertFalse(Utils.isNumber("5,4"));
    }

    @Test
    public void isNumberWrongTest(){
        Assert.assertFalse(Utils.isNumber("Hello"));
        Assert.assertFalse(Utils.isNumber("o5"));
    }
}
