package ua.expo.controller.command;

import org.apache.log4j.Logger;
import ua.expo.model.entity.Exposition;
import ua.expo.model.service.ExpositionService;
import ua.expo.model.service.util.Utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Calculate total price of tickets and send redirect to payment page
 * @author andrii
 */
public class Buy implements Command {

    private static final Logger LOGGER = Logger.getLogger(Buy.class);
    private ExpositionService expoService;

    public Buy() {
        this.expoService = ExpositionService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request) {
        LOGGER.debug("User try to by tickets. User login=" + request.getSession().getAttribute("login"));
        String expoId;
        Exposition exposition;
        String ticketsCount = request.getParameter("tickets_count");
        if (ticketsCount == null) {
            expoId = request.getParameter("expo_id");
            exposition = expoService.getExposition(expoId);
            if (exposition == null) {
                LOGGER.debug("Parameter 'expo_id' is null. Search in session");
                exposition = (Exposition) request.getSession().getAttribute("expo_buy");
                if (exposition == null) {
                    throw new RuntimeException("There is no exposition with such id!");
                }
            }
            request.getSession().setAttribute("expo_buy", exposition);
            return "/WEB-INF/user/buy.jsp";
        }

        exposition = (Exposition) request.getSession().getAttribute("expo_buy");
        if (exposition == null) {
            throw new RuntimeException("There is no exposition with such id!");
        }
        request.getSession().setAttribute("price", getTotalPrice(exposition, ticketsCount));
        request.getSession().setAttribute("tickets_count", Integer.parseInt(ticketsCount));
        LOGGER.info("User choose the count of tickets. Make forward to payment page.");
        return "/WEB-INF/user/payment.jsp";
    }

    private int getTotalPrice(Exposition exposition, String ticketsCount) {
        if (Utils.isNumber(ticketsCount)) {
            int tickets = Integer.parseInt(ticketsCount);
            if (tickets > 0) {
                return exposition.getPrice() * tickets;
            }
        }
        throw new RuntimeException("Wrong tickets count!");
    }


}
