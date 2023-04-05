package com.attractorschool.imurab.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.attractorschool.imurab.util.enums.Role.*;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        if (principal.getUser().getRole().equals(ROLE_ADMIN)) {
            response.sendRedirect("/users");
        } else if (principal.getUser().getRole().equals(ROLE_FARMER)) {
            response.sendRedirect("/");
        } else if (principal.getUser().getRole().equals(ROLE_MODERATOR)) {
            response.sendRedirect("/news");
        } else if (principal.getUser().getRole().equals(ROLE_OPERATOR)) {
            response.sendRedirect("/users");
        }
    }
}
