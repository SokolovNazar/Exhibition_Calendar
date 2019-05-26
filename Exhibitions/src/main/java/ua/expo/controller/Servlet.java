package ua.expo.controller;

import org.apache.log4j.Logger;
import ua.expo.controller.command.*;
import ua.expo.controller.command.Exception;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    final static Logger LOGGER = Logger.getLogger(Servlet.class);
    private Map<String, Command> commands;

    public void init(ServletConfig config){
        LOGGER.info("In servlet init method.");
        config.getServletContext().setAttribute("loggedUsers", new HashSet<String>());
        commands = new HashMap<>();
        commands.put("logout", new LogOut());
        commands.put("login", new LogIn());
        commands.put("registration", new Registration());
        commands.put("exception" , new Exception());
        commands.put("r/exposition" , new Exposition());
        commands.put("r/admin/stat",new AdminPage());
        commands.put("r/admin/halls", new AdminHalls());
        commands.put("r/admin/expositions", new AdminExpo());
        commands.put("r/user/buy", new Buy());
        commands.put("r/user/buy/payment/conf", new PaymentConfirm());
        commands.put("error", e -> "/WEB-INF/error.jsp");
        commands.put("r/forbidden", e -> "/WEB-INF/admin/forbidden.jsp");
        commands.put("r/user/buy/payment", e -> "/WEB-INF/user/payment.jsp");
        commands.put("r/user/tickets", new UserTickets());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/app/", "");
        Command command = commands.getOrDefault(path ,
                (r)->"/index.jsp");
        String page = command.execute(request);
        if(page.contains("redirect")){
        response.sendRedirect(page.replace("redirect:", ""));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}