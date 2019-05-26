package ua.expo.model.service;

import org.apache.log4j.Logger;
import ua.expo.model.dao.DaoFactory;
import ua.expo.model.dao.impl.JDBCTicketDao;
import ua.expo.model.dao.impl.JDBCUserDao;
import ua.expo.model.entity.Exposition;
import ua.expo.model.entity.Ticket;
import ua.expo.model.entity.User;
import ua.expo.model.entity.enums.Role;
import ua.expo.model.service.util.HashingPasswordUtil;
import ua.expo.model.service.util.RegexContainer;
import ua.expo.model.service.util.Utils;

import java.util.List;
import java.util.Optional;

/**
 * Service class. Verify data before pass it to JDBCUserDay
 * @author andrii
 */
public class UserService {

    private final static Logger LOGGER = Logger.getLogger(UserService.class);

    private static volatile UserService instance;
    private JDBCUserDao userDao;
    private JDBCTicketDao ticketDao;
    private HashingPasswordUtil hashingUtil;

    private UserService(){
        userDao = DaoFactory.getInstance().createUserDao();
        ticketDao = DaoFactory.getInstance().createTicketDao();
        hashingUtil = new HashingPasswordUtil();
    }

    public static UserService getInstance(){
        if(instance == null){
            synchronized (UserService.class){
                if(instance == null){
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    /**
     * @see JDBCUserDao#insert(User)
     * If data do not pass validation then throws proper exseption
     */
    public void createUser(String name, String surname, String email, String login, String password){
        LOGGER.info("Request to create new user with login=" + login);
        User.Builder builder = new User.Builder();
        User user = builder.setName(name)
                .setSurname(surname)
                .setEmail(email)
                .setRole(Role.USER)
                .setLogin(login)
                .setPassword(hashingUtil.encryptionSHA256(password))
                .build();
        if(validateData(user)) {
            LOGGER.info("User data is valid. Make request to user dao.");
            userDao.insert(user);
            return;
        }
        LOGGER.info("Users data is not correct.");
        throw new IllegalArgumentException("Please, check all date and correct it.");
    }

    /**
     * @see JDBCTicketDao#getUserTickets(User)
     */
    public List<Ticket> getUserTickets(User user){
        return ticketDao.getUserTickets(user);
    }

    /**
     * @see JDBCTicketDao#insert(Ticket)
     */
    public void buyTickets(User user, Exposition exposition, int count){
            ticketDao.insert(new Ticket.Builder()
                    .setCount(count)
                    .setUser(user)
                    .setExposition(exposition)
                    .build());
    }

    /**
     * @see JDBCUserDao#getByLogin(String)
     */
    public Optional<User> getByLogin(String login){
        if(login == null){
            return Optional.empty();
        }
        return Optional.ofNullable(userDao.getByLogin(login));
    }

    /**
     * Compare hashed password contained id User instanse
     * and string password (not hashed)
     */
    public boolean checkPassword(User user, String password) {
        return hashingUtil.isEqualsSHA256(password, user.getPassword());
    }

    private boolean validateData(User user) {
        if(!Utils.regexCheck(user.getName(), RegexContainer.NAME_RG)){
            return false;
        }
        if(!Utils.regexCheck(user.getSurname(), RegexContainer.SURNAME_RG)){
            return false;
        }
        return user.getLogin().length() >= 5 && user.getPassword().length() >= 7;
    }

    public void setUserDao(JDBCUserDao userDao){
        this.userDao = userDao;
    }

    public void setTicketDao(JDBCTicketDao ticketDao){
        this.ticketDao = ticketDao;
    }
}
