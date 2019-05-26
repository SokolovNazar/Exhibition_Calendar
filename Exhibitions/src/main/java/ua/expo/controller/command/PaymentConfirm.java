package ua.expo.controller.command;

import ua.expo.model.entity.Exposition;
import ua.expo.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PaymentConfirm implements Command {
    private UserService userService;

    public PaymentConfirm() {
        this.userService = UserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("expo_buy");
        if (obj instanceof Exposition) {
            Exposition expo = (Exposition) obj;
            userService.buyTickets(
                    userService.getByLogin(((String) request.getSession().getAttribute("login"))).get(),
                    expo,
                    (Integer) request.getSession().getAttribute("tickets_count")
            );
        }
        HttpSession session = request.getSession();
        session.removeAttribute("price");
        session.removeAttribute("expo_buy");
        session.removeAttribute("tickets_count");
        return "redirect:/app/r/exposition";
    }
}
