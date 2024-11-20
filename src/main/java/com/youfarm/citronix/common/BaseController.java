package com.youfarm.citronix.common;


import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseController {

    protected Long getUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }

    protected String getToken(HttpServletRequest request) {
        return (String) request.getAttribute("token");
    }
}

