package ua.expo.controller.command;

import ua.expo.model.entity.User;
import ua.expo.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Set in attribute all user tickets.
 */
public class UserTickets implements Command {
    private UserService userService;

    public UserTickets(){
        this.userService = UserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request) {
        User user = userService.getByLogin(
                (String)request.getSession().getAttribute("login")).get();
        request.setAttribute("tickets", userService.getUserTickets(user));
        request.setAttribute("user", user);
        return "/WEB-INF/user/tickets.jsp";
    }
}
