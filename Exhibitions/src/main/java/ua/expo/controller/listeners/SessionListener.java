package ua.expo.controller.listeners;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {

    private static final Logger LOGGER = Logger.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        LOGGER.info("Session created");
    }

    /**
     * When users session ends, delete this user from set
     * of active users
     */
    @SuppressWarnings("unchecked")
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        System.out.println("session destroyed");
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext().getAttribute("loggedUsers");
        String login = (String) httpSessionEvent.getSession().getAttribute("login");
        loggedUsers.remove(login);
        LOGGER.info("Destruction of session. Delete user from loggedUsers  set. User login=" + login);
        httpSessionEvent.getSession().setAttribute("login", null);
        httpSessionEvent.getSession().setAttribute("role", null);
        httpSessionEvent.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
    }
}
