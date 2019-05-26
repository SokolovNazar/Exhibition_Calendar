package ua.expo.model.service.util;

public class Utils {
    public static boolean isNumber(String number){
        return regexCheck(number, RegexContainer.NUMBERS_RG );
    }

    public static boolean isNotNull(String ... strings){
        for (String string : strings) {
            if(string == null){
                return false;
            }
        }
        return true;
    }

    public static boolean regexCheck(String item, String regex){
        if(item == null){
            return false;
        }
        return item.matches(regex);
    }
}
