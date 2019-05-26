package ua.expo.controller.command;

import org.apache.log4j.Logger;
import ua.expo.model.entity.User;
import ua.expo.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Read parameters from login page. If such login contains in DB,
 * then do check password.
 * If any data are invalid then  return back to the login page
 */
public class LogIn implements Command {

    private static final Logger LOGGER = Logger.getLogger(LogIn.class);
    private UserService userService;

    public LogIn() {
        this.userService = UserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || login.equals("")
                || password == null || password.equals("")) {
            return "/login.jsp";
        }
        Optional<User> user = userService.getByLogin(login);
        if (user.isPresent() && userService.checkPassword(user.get(), password)) {
            if (CommandUtil.containsInLogged(request, login)) {
                LOGGER.warn("Logged user try to login. User login="
                        + request.getSession().getAttribute("login"));
                return "/WEB-INF/error.jsp";
            }
            CommandUtil.logInUser(request, user.get().getLogin(), user.get().getRole());
            LOGGER.info("User log in system. User login=" + user.get().getLogin());
            return "redirect:/app/r/exposition";
        }
        LOGGER.info("User entered wrong login or password");
        request.setAttribute("message", "wrong.login.password");
        return "/login.jsp";
    }

}