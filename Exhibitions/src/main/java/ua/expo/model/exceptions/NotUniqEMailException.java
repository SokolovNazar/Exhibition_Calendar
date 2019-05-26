package ua.expo.model.exceptions;

/**
 * Exception which marks that entered email already used
 * @author andrii
 */
public class NotUniqEMailException extends RuntimeException {
    private String login;
    private String name;
    private String surname;
    private String email;

    public NotUniqEMailException(String login, String name, String surname, String email){
        super("Email \"" + email + "\" already  in use!");
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
}
