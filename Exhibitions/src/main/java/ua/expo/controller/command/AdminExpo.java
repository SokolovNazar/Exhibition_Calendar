package ua.expo.controller.command;

import ua.expo.model.service.ExpositionService;
import ua.expo.model.service.HallsService;
import ua.expo.model.service.util.Utils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Processing administrator action under list of exposition.
 * Receive parameters and pass it to exposition service.
 * @author andrii
 */
public class AdminExpo implements Command {
    private HallsService hallsService;
    private ExpositionService expoService;
    private Map<String, Consumer<HttpServletRequest>> commands;

    public AdminExpo() {
        this.commands = new HashMap<>();
        this.expoService = ExpositionService.getInstance();
        this.hallsService = HallsService.getInstance();
        commands.put("add", this::add);
        commands.put("delete", this::delete);
        commands.put("update", this::update);
    }

    @Override
    public String execute(HttpServletRequest request) {
        Consumer<HttpServletRequest> command = commands.get(request.getParameter("command"));
        LocalDate now = LocalDate.now();
        request.setAttribute("date_from", now);
        request.setAttribute("date_to", now.plusYears(2));
        request.setAttribute("halls", hallsService.getHalls());
        request.setAttribute("adminPage", "/WEB-INF/admin/adminExpositions.jsp");
        if (command != null) {
            command.accept(request);
            request.setAttribute("expositions", expoService.getExpositions());
            return "redirect:/app/r/admin/expositions";
        }
        request.setAttribute("expositions", expoService.getExpositions());
        return "/WEB-INF/admin/admin.jsp";
    }

    private void add(HttpServletRequest request) {
        String theme = request.getParameter("theme");
        String shortDescription = request.getParameter("short");
        String fullDescription = request.getParameter("full");
        String price = request.getParameter("price");
        String date = request.getParameter("date");
        String date_to = request.getParameter("date_to");
        String hallId = request.getParameter("hall_id");
        try {
            if(Utils.isNotNull(theme, shortDescription, date, date_to)
                    && Utils.isNumber(hallId)
                    && Utils.isNumber(price)) {
                expoService.add(theme, shortDescription, fullDescription,
                        price, date, date_to, hallId);
                request.getSession().setAttribute("expo_message", "Exposition added");
            }
        } catch (IllegalArgumentException e){
            request.getSession().setAttribute("expo_message", e.getMessage());
        }
    }

    private void delete(HttpServletRequest request) {
        String expoId = request.getParameter("expo_id");
        if(Utils.isNumber(expoId)) {
            expoService.delete(expoId);
        }
    }

    private void update(HttpServletRequest request) {
        String expoId = request.getParameter("expo_id");
        String theme = request.getParameter("theme");
        String shortDescription = request.getParameter("short");
        String fullDescription = request.getParameter("full");
        String price = request.getParameter("price");
        String date = request.getParameter("date");
        String date_to = request.getParameter("date_to");
        String hallId = request.getParameter("hall_id");
        try {
            if (Utils.isNotNull(theme, shortDescription, date, date_to)
                    && Utils.isNumber(price) && Utils.isNumber(hallId) && Utils.isNumber(expoId)) {
                expoService.update(expoId, theme, shortDescription, fullDescription,
                        price, date, date_to, hallId);
                request.getSession().setAttribute("expo_message", "Exposition changed");
            }
        } catch (IllegalArgumentException e){
            request.getSession().setAttribute("expo_message", e.getMessage());
        }
    }
}

