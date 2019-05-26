package ua.expo.modelTests.serviceTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.expo.model.dao.impl.JDBCTicketDao;
import ua.expo.model.dao.impl.JDBCUserDao;
import ua.expo.model.entity.Exposition;
import ua.expo.model.entity.Ticket;
import ua.expo.model.entity.User;
import ua.expo.model.entity.enums.Role;
import ua.expo.model.service.UserService;
import ua.expo.model.service.util.HashingPasswordUtil;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService userService;
    @Mock
    private JDBCTicketDao ticketDao;

    @Mock
    private JDBCUserDao userDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userService = UserService.getInstance();
        userService.setUserDao(userDao);
        userService.setTicketDao(ticketDao);
    }

    @Test
    public void createUserTest() {
        HashingPasswordUtil util = new HashingPasswordUtil();
        User testUser = new User.Builder()
                .setName("Name")
                .setSurname("Surname")
                .setEmail("test@email.com")
                .setPassword("password")
                .setLogin("login")
                .setRole(Role.USER)
                .build();

        ArgumentCaptor<User> valueCaptor = ArgumentCaptor.forClass(User.class);
        doNothing().when(userDao).insert(valueCaptor.capture());
        userService.createUser(testUser.getName(),
                testUser.getSurname(), testUser.getEmail(),
                testUser.getLogin(), testUser.getPassword());
        testUser.setPassword(util.encryptionSHA256(testUser.getPassword()));
        Assert.assertEquals(testUser, valueCaptor.getValue());
    }

    @Test
    public void getByLoginNullTest() {
        String login = null;
        doThrow(Exception.class).when(userDao).getByLogin(anyString());
        Optional<User> user = userService.getByLogin(login);
        verify(userDao, never()).getByLogin(anyString());
        Assert.assertFalse(user.isPresent());
    }

    @Test
    public void getyLoginTest() {
        String login = "login";
        when(userDao.getByLogin(login)).thenReturn(new User.Builder().setLogin(login).build());
        Optional<User> user = userService.getByLogin(login);
        verify(userDao, times(1)).getByLogin(login);
        Assert.assertTrue(user.isPresent());
    }

    @Test
    public void buyTicketTest() {
        User testUser = new User.Builder().setId(1).build();
        Exposition testExposition = new Exposition.Builder().setId(1).build();
        int count = 2;
        Ticket testTicket = new Ticket.Builder()
                .setUser(testUser)
                .setExposition(testExposition)
                .setCount(count).build();
        ArgumentCaptor<Ticket> valueCaptor = ArgumentCaptor.forClass(Ticket.class);
        doNothing().when(ticketDao).insert(valueCaptor.capture());
        userService.buyTickets(testUser, testExposition, count);
        verify(ticketDao, times(1)).insert(any(Ticket.class));
        Assert.assertEquals(testTicket, valueCaptor.getValue());
    }
}
