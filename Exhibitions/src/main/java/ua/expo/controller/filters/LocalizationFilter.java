package ua.expo.controller.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**If request contains parameter 'lang', then read it
 * and set proper locale to user session
 * @author andrii
 */
public class LocalizationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(LocalizationFilter.class);
    private Map<String, Locale> locales;
    @Override
    public void init(FilterConfig filterConfig){
        locales = new HashMap<>();
        locales.put("ua", new Locale("uk", "UA"));
        locales.put("en", new Locale("en", "US"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String lang = servletRequest.getParameter("lang");
        if (lang != null) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            LOGGER.debug("Trying to change locale to lang=" + lang);
            if(locales.containsKey(lang)){
                Config.set(request.getSession(), Config.FMT_LOCALE, locales.get(lang));
                LOGGER.debug("Change locale to " + locales.get(lang).getCountry());
            }
            response.sendRedirect(request.getHeader("referer"));
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }



    @Override
    public void destroy(){
    }
}
