package ua.expo.model.service.util;

public interface RegexContainer {
    public static final String NAME_RG = "[А-З[Й-Щ]ІЇЄҐЮЯA-Z]{1}[a-zа-яіїєґ]{1,20}([-]{1}[A-ZА-З[Й-Я]ІЇЄҐ]{1}[a-zа-яіїєґ]{1,20})?";
    public static final String SURNAME_RG ="[A-ZА-З[Й-Я]ІЇЄҐ]{1}[a-zа-яіїєґ]{1,20}([-]{1}[A-ZА-З[Й-Я]ІЇЄҐ]{1}[a-zа-яіїєґ]{1,20})?";
    public static final String NUMBERS_RG = "^([+\\-])?\\d+$";
}
