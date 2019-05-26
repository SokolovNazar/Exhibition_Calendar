package ua.expo.controller.filters;


import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

/**
 * Set request and response character encoding to UTF-8.
 * @author andrii
 */
public class EncodingFilter implements Filter {

    private final static Logger LOGGER = Logger.getLogger(EncodingFilter.class);
    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");
        LOGGER.debug("Change encoding and charset to UTF-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }
}
