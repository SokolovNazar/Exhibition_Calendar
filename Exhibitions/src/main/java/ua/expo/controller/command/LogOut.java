package ua.expo.controller.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Delete user from set of active users
 * @see CommandUtil#logOutUser(HttpServletRequest, String)
 * @author andrii
 */
public class LogOut implements Command {

    private static final Logger LOGGER = Logger.getLogger(LogOut.class);

    @Override
    public String execute(HttpServletRequest request) {
        if(!CommandUtil.isUserLogged(request)){
            LOGGER.warn("Unlogged user try to log out");
            return "/WEB-INF/error.jsp";
        }
        String login = (String) request.getSession().getAttribute("login");
        LOGGER.info("User try to log out. User login=" + login);
        CommandUtil.logOutUser(request, login);
        return "redirect:/index.jsp";
    }
}
