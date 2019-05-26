package ua.expo.controller.command;

import ua.expo.model.entity.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class CommandUtil {

    /**
     * @return true if equals specified role with role from user session
     */
    public static boolean hasRole(HttpServletRequest request, Role role) {
        Object roleObj = request.getSession().getAttribute("role");
        return role == (roleObj != null ? Role.valueOf(roleObj.toString()) : Role.UNKNOWN);
    }

    /**
     *
     * @return true if user login contains set of active users
     */
    @SuppressWarnings("unchecked")
    public static boolean isUserLogged(HttpServletRequest request) {
        String login = (String) request.getSession().getAttribute("login");
        if (login == null) {
            return false;
        }
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession()
                .getServletContext().getAttribute("loggedUsers");
        return  loggedUsers.contains(login);
    }

    /**
     *
     * @return true if user with such login already contains if set of active users
     */
    @SuppressWarnings("unchecked")
    static boolean containsInLogged(HttpServletRequest request, String login){
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession()
                .getServletContext().getAttribute("loggedUsers");
        return loggedUsers.stream().anyMatch(login::equals);
    }

    /**delete user from set of active users.
     * Also remove user`s data from his session
     *
     */
    @SuppressWarnings("unchecked")
    static void logOutUser(HttpServletRequest request, String login) {
        HashSet<String> loggedUsers = (HashSet<String>)
                request.getSession().getServletContext().getAttribute("loggedUsers");
        loggedUsers.remove(login);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        HttpSession session = request.getSession();
        session.removeAttribute("login");
        session.removeAttribute("role");
    }

    /**
     * Add user login and role to his session.
     * Also add him to set of active users.
     */
    @SuppressWarnings("unchecked")
    static void logInUser(HttpServletRequest request, String login, Role role) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession()
                .getServletContext().getAttribute("loggedUsers");
        loggedUsers.add(login);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        HttpSession session = request.getSession();
        session.setAttribute("login", login);
        session.setAttribute("role", role);
    }
}
