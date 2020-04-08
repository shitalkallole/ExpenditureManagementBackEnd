package org.project.expendituremanagement.filter;

import org.project.expendituremanagement.dto.HttpSession;
import org.project.expendituremanagement.dto.ValidateSessionResponse;
import org.project.expendituremanagement.serviceinterface.HttpSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class SessionRequestFilter extends OncePerRequestFilter {
    @Autowired
    private HttpSessionService httpSessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authUserId=httpServletRequest.getHeader("userId");
        String authSessionId=httpServletRequest.getHeader("sessionId");

        if (authUserId != null && authSessionId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                HttpSession httpSession = new HttpSession();

                httpSession.setUserId(authUserId);
                httpSession.setSessionId(UUID.fromString(authSessionId));

                ValidateSessionResponse validateSessionResponse = httpSessionService.validateSession(httpSession);

                if (validateSessionResponse.getSessionActiveOrNot()) {
                    //the code which is usually done by spring after Authentication
                    //here we are saying that this request from valid user. if you want to check you can go to below constructor of UsernamePasswordAuthenticationToken(in that constructor it is saying this is from valid user by setting authenticated=true)
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, null);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        filterChain.doFilter(httpServletRequest, httpServletResponse);//if you think then we should add this line in above if,but if you add it to above if then in /user/validate request there is no userId and sessionId(this api default allowed) then it will not go in if and code will not go forward
    }
}
