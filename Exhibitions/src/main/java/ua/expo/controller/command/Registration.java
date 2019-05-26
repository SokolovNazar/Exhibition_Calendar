package ua.expo.controller.command;

import org.apache.log4j.Logger;
import ua.expo.model.entity.enums.Role;
import ua.expo.model.exceptions.NotUniqEMailException;
import ua.expo.model.exceptions.NotUniqLoginException;
import ua.expo.model.service.UserService;
import ua.expo.model.service.util.Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Read input data from registration page.
 * If all data correct, try to add new user to DB via user service
 * @see UserService#createUser(String, String, String, String, String)
 * If some data (login, email) are not unique, then inform user about it.
 *
 */
public class Registration implements Command {

    private static final Logger LOGGER = Logger.getLogger(Registration.class);

    private UserService userService;

    public Registration() {
        this.userService = UserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("name_min", 2);
        request.setAttribute("name_max", 20);
        request.setAttribute("surname_min", 2);
        request.setAttribute("surname_max", 20);
        request.setAttribute("login_min", 6);
        request.setAttribute("login_max", 20);
        request.setAttribute("password_min", 8);
        request.setAttribute("password_max", 30);
        request.setAttribute("login_ptrn", "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String login = request.getParameter("login");
        if (!Utils.isNotNull(name, surname, email, login, password)) {
            return "/registration.jsp";
        }
        try {
            if (CommandUtil.isUserLogged(request)) {
                LOGGER.warn("Logged user try to sign up.");
                return "/WEB-INF/error.jsp";
            }
            userService.createUser(name, surname, email, login, password);
            CommandUtil.logInUser(request, login, Role.USER);
        } catch (NotUniqLoginException e) {
            request.setAttribute("message", "not.uniq.login");
            return setAttributes(request, name, surname, email, login);
        } catch (NotUniqEMailException e) {
            request.setAttribute("message", "not.uniq.email");
            return setAttributes(request, name, surname, email, login);
        } catch (java.lang.Exception e) {
            request.setAttribute("message", "registration.invalid.data");
            return setAttributes(request, name, surname, email, login);
        }
        return "redirect:/app/r/exposition";
    }

    private String setAttributes(HttpServletRequest request, String name, String surname, String email, String login) {
        request.setAttribute("name", name);
        request.setAttribute("surname", surname);
        request.setAttribute("email", email);
        request.setAttribute("login", login);
        return "/registration.jsp";
    }
}
