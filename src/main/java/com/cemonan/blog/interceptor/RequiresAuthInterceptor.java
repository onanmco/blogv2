package com.cemonan.blog.interceptor;

import com.cemonan.blog.domain.Token;
import com.cemonan.blog.domain.User;
import com.cemonan.blog.exception.AuthUserNotFoundException;
import com.cemonan.blog.exception.TokenNotFoundException;
import com.cemonan.blog.exception.TokenNotProvidedException;
import com.cemonan.blog.repository.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class RequiresAuthInterceptor implements HandlerInterceptor {

    private final WebApplicationContext context;
    private final AuthTokenRepository authTokenRepository;

    @Autowired
    public RequiresAuthInterceptor(WebApplicationContext context, AuthTokenRepository authTokenRepository) {
        this.context = context;
        this.authTokenRepository = authTokenRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String providedToken = request.getHeader("X-AUTH-TOKEN");

        if (providedToken == null) {
            throw new TokenNotProvidedException("");
        }

        Token token = authTokenRepository.getTokenByUUID(UUID.fromString(providedToken));

        if (token == null) {
            throw new TokenNotFoundException("");
        }

        User user = authTokenRepository.getUserByToken(token);

        if (user == null) {
            authTokenRepository.delete(token);
            throw new AuthUserNotFoundException("");
        }

        context.getServletContext().setAttribute("auth.token", token);
        context.getServletContext().setAttribute("auth.user", user);

        return true;
    }
}
