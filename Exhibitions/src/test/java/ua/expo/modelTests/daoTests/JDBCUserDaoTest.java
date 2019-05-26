package ua.expo.modelTests.daoTests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.expo.model.dao.DaoFactory;
import ua.expo.model.dao.impl.JDBCUserDao;
import ua.expo.model.entity.User;
import ua.expo.model.entity.enums.Role;
import ua.expo.model.exceptions.NotUniqEMailException;
import ua.expo.model.exceptions.NotUniqLoginException;

import java.util.List;

public class JDBCUserDaoTest {

    private static JDBCUserDao userDao;

    @BeforeClass
    public static void init() {
        userDao = DaoFactory.getInstance().createUserDao();
    }

    @Test
    public void insertTest(){
        User testUser = new User.Builder().setRole(Role.USER)
                .setName("TestName")
                .setSurname("TestSurname")
                .setLogin("TestLogin")
                .setPassword("TestPassword")
                .setEmail("test@email.test")
                .build();

        userDao.insert(testUser);

        List<User>  list = userDao.getAll();
        for (User user : list) {
            if(user.getLogin().equals(testUser.getLogin())){
                testUser.setId(user.getId());
                Assert.assertEquals(testUser, user);
                userDao.delete(testUser);
                return;
            }
        }
        Assert.fail();
    }

    @Test(expected = NotUniqEMailException.class)
    public void insertWithEmailTest(){
        User testUser = new User.Builder().setRole(Role.USER)
                .setName("TestName")
                .setSurname("TestSurname")
                .setLogin("testLogin")
                .setPassword("TestPassword")
                .setEmail("TarasG@post.net")
                .build();
        userDao.insert(testUser);
        Assert.assertFalse(userDao.getAll().stream().anyMatch(e -> e.getLogin().equals(testUser.getLogin())));
    }

    @Test(expected = NotUniqLoginException.class)
    public void insertWithLoginTest(){
        User testUser = new User.Builder().setRole(Role.USER)
                .setName("TestName")
                .setSurname("TestSurname")
                .setLogin("admin")
                .setPassword("TestPassword")
                .setEmail("test@email.test")
                .build();
        userDao.insert(testUser);
        Assert.assertFalse(userDao.getAll().stream().anyMatch(e -> e.getLogin().equals(testUser.getLogin())));

    }

    @Test
    public void getByLoginTest(){
        User testUser = new User.Builder().setRole(Role.USER)
                .setName("TestName")
                .setSurname("TestSurname")
                .setLogin("TestLogin")
                .setPassword("TestPassword")
                .setEmail("test@email.test")
                .build();

        userDao.insert(testUser);

        User user = userDao.getByLogin(testUser.getLogin());
        if(user == null){
            Assert.fail();
            return;
        }
        testUser.setId(user.getId());
        Assert.assertEquals(testUser, user);
        userDao.delete(testUser);
    }

    @Test
    public void updateTest(){
        User testUser = new User.Builder().setRole(Role.USER)
                .setName("TestName")
                .setSurname("TestSurname")
                .setLogin("TestLogin")
                .setPassword("TestPassword")
                .setEmail("test@email.test")
                .build();

        userDao.insert(testUser);
        testUser = userDao.getByLogin(testUser.getLogin());
        testUser.setPassword("newTestPassword");
        userDao.update(testUser);

        User user = userDao.getByLogin(testUser.getLogin());
        Assert.assertEquals(user, testUser);
        Assert.assertEquals(testUser.getLogin(), user.getLogin());
        Assert.assertEquals(testUser.getId(), user.getId());
        Assert.assertEquals(testUser.getPassword(), user.getPassword());
        userDao.delete(user);
    }


}
