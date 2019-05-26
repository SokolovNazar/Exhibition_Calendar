package ua.expo.model.dao.mapper;

import ua.expo.model.entity.User;
import ua.expo.model.entity.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements ObjectMapper<User> {

    private static volatile UserMapper instance;

    private UserMapper(){}

    public static UserMapper getInstance(){
        if(instance == null){
            synchronized (ExpositionMapper.class){
                if(instance == null){
                    instance = new UserMapper();
                }
            }
        }
        return instance;
    }

    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new User.Builder()
                .setId(resultSet.getInt("users.id"))
                .setName(resultSet.getString("users.name"))
                .setSurname(resultSet.getString("users.surname"))
                .setEmail(resultSet.getString("users.email"))
                .setLogin(resultSet.getString("users.login"))
                .setPassword(resultSet.getString("users.password"))
                .setRole(Role.valueOf(resultSet.getString("users.role")))
                .build();
    }

    @Override
    public User makeUnique(Map<Integer, User> cache, User user) {
        cache.putIfAbsent(user.getId(), user);
        return cache.get(user.getId());
    }
}
