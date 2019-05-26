package ua.expo.controller.filters;

import org.apache.log4j.Logger;
import ua.expo.controller.command.CommandUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Check if user contains in set of active user.
 * If wrong, then send redirect to login page
 * @author andrii
 */
public class AuthorizationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!CommandUtil.isUserLogged(request)) {
            LOGGER.info("Unregistered user try to gain access to privileged resources.");
            response.sendRedirect("/app/login");
            return;
        }
        LOGGER.debug("User is logged. Login=" + request.getSession().getAttribute("login"));
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
