package ua.expo.controller.command;

import ua.expo.model.service.HallsService;

import javax.servlet.http.HttpServletRequest;

/**
 * Set as attribute statistics about users, halls and expositions
 */
public class AdminPage implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("totalExpositions", HallsService.getInstance().getNumberOfRows());
        request.setAttribute("totalHalls", HallsService.getInstance().getHalls().size());
        request.setAttribute("adminPage","/WEB-INF/admin/adminStat.jsp");
        return "/WEB-INF/admin/admin.jsp";
    }
}
