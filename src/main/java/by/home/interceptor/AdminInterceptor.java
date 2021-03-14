package by.home.interceptor;

import by.home.service.XTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private XTokenService xTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("X-Token");
        if (xTokenService.isAdmin(header)) {
            log.info("Admin check passed with token "+header+".");
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            log.error("Admin check by token was failed! Token ="+header+".");
            return false;
        }
    }
}
