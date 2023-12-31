package com.login.jwt.security;
import antlr.Token;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
    Authentication 객체를 생성해 SecurityContext에 등록
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final TokenProvider tokenProvider;

    public JwtAuthenticationFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        try {
            String token=parserBearerToken(request);

            if(token!=null && !token.equalsIgnoreCase("null")){
                String userId=tokenProvider.validateAndGetUserId(token);

                AbstractAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                setAuthenticationToSecurityContext(authenticationToken);
            }
        }catch (Exception e){
            logger.error("Could not set user Authentication in security context",e);
        }

        filterChain.doFilter(request,response); // 다음 필터 진행

    }

    private void setAuthenticationToSecurityContext(AbstractAuthenticationToken authenticationToken){
        SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(securityContext);
    }

    private String parserBearerToken(HttpServletRequest request){
        String bearerToken=request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }
}
