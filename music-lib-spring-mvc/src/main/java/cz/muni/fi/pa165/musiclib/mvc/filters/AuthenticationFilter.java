package cz.muni.fi.pa165.musiclib.mvc.filters;

import cz.muni.fi.pa165.musiclib.dto.UserAuthenticationDTO;
import cz.muni.fi.pa165.musiclib.dto.UserDTO;
import cz.muni.fi.pa165.musiclib.facade.UserFacade;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Restricts all sites of application from unauthenticated users.
 *
 * @author Zuzana Dankovcikova
 * @version 08/12/2015
 */
@WebFilter(urlPatterns = {"/song/*", "/album/*", "/musician/*", "/genre/*", "/user/*"})
public class AuthenticationFilter implements Filter {

    final static Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

//        Cookie[] cookies = request.getCookies();
//
//        if (cookies != null) {
//            if (!checkForCookie(cookies, "auth")) {
//                response.sendRedirect(request.getContextPath() + "/login");
//            }
//        } else {
//            log.error("user has disabled cookies");
//            response.sendRedirect(request.getContextPath() + "/logining");
//        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private boolean checkForCookie(Cookie[] cookies, String cookieName) {
        for (Cookie ck : cookies) {
            if (cookieName.equals(ck.getName())) {
                return true;
            }
        }
        return false;
    }
}
