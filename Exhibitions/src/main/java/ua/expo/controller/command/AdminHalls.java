package ua.expo.controller.command;

import ua.expo.model.service.HallsService;
import ua.expo.model.service.util.Utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Processing administrator action under list of exhibition halls.
 * Receive parameters and pass it to exhibition hall service.
 * @author andrii
 */
public class AdminHalls implements Command {
    private Map<String, Consumer<HttpServletRequest>> commands;
    private HallsService hallsService;

    public AdminHalls(){
        this.hallsService = HallsService.getInstance();
        commands = new HashMap<>();
        commands.put("add", this::add);
        commands.put("update", this::update);
        commands.put("delete", this::delete);
    }
    @Override
    public String execute(HttpServletRequest request) {
        Consumer<HttpServletRequest> command = commands.get(request.getParameter("command"));
        request.setAttribute("adminPage","/WEB-INF/admin/adminHalls.jsp");
        if(command != null){
            command.accept(request);
            request.setAttribute("halls", hallsService.getHalls());
            return "redirect:/app/r/admin/halls";
        }
        request.setAttribute("halls", hallsService.getHalls());
        return "/WEB-INF/admin/admin.jsp";
    }

    private void add(HttpServletRequest request){
        String name = request.getParameter("name");
        String info = request.getParameter("information");
        if(Utils.isNotNull(name, info)) {
            hallsService.add(name, info);
        }
    }

    private void delete(HttpServletRequest request){
        String idStr = request.getParameter("id");
        if(Utils.isNumber(idStr)) {
            hallsService.delete(idStr);
        }
    }

    private void update(HttpServletRequest request){
        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String info = request.getParameter("information");
        if(Utils.isNotNull(name, info) && Utils.isNumber(idStr)) {
            hallsService.update(idStr, name, info);
        }
    }
}
