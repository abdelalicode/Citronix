package com.youfarm.citronix.common.interceptor;

import com.youfarm.citronix.common.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private TokenUtil tokenUtil;

    public AuthInterceptor(TokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader == null || !tokenHeader.startsWith("Bearer ")) {
        sendErrorResponse(response, "Authentication required", HttpStatus.UNAUTHORIZED);
            return false;
        }

        String token = tokenHeader.replace("Bearer ", "");

        if(!tokenUtil.isAuthenticated(token)) {
            sendErrorResponse(response, "Authentication required", HttpStatus.UNAUTHORIZED);
            return false;
        }

        Long userId = tokenUtil.getUserFromToken(token);
        request.setAttribute("userId", userId);
        request.setAttribute("token", token);

        return true;

    }

    private void sendErrorResponse(HttpServletResponse response, String message, HttpStatus status) throws IOException, IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(message);
        response.getWriter().flush();
    }


}
