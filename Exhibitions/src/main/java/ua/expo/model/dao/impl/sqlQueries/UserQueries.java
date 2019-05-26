package ua.expo.model.dao.impl.sqlQueries;

public interface UserQueries {
    String USER_GET_BY_ID = "SELECT * FROM ExpositionProject.users where id=?;";
    String USER_GET_BY_LOGIN = "SELECT * FROM ExpositionProject.users where BINARY users.login = ?;";
    String USER_GET_BY_LOGIN_NO_CASE = "SELECT * FROM ExpositionProject.users where users.login = ?;";
    String USER_GET_BY_EMAIL_NO_CASE = "SELECT * FROM ExpositionProject.users where users.email = ?;";
    String USER_INSERT = "insert into ExpositionProject.users (name, surname, email, login, password, role) " +
            "values (?, ?, ?, ?, ?, ?);";
    String USER_UPDATE = "update ExpositionProject.users set name=?, surname=?, email=?, " +
            "login=?,password=?,role=? where id=?;";
    String USER_DELETE = "delete from ExpositionProject.users where id = ?;";
    String USER_GET_ALL = "select * from ExpositionProject.users";

}
