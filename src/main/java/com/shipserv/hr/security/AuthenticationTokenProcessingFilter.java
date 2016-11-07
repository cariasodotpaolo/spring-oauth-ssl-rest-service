package com.shipserv.hr.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger("org.test.security.AuthenticationTokenProcessingFilter");

    public void doFilter(ServletRequest request, 
    					ServletResponse response,
    					FilterChain chain) throws IOException, ServletException {
    	
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
    	
        Map<String, String[]> params = request.getParameterMap();
        
        if (params.containsKey("token")) {
        	String strToken = params.get("token")[0]; // grab the first "token" parameter
            logger.info("Token: " + strToken);
            
            chain.doFilter(request, response);
            
        } else { 
           httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/login");
        }
        

        /*if (parms.containsKey("token")) {
            String strToken = parms.get("token")[0]; // grab the first "token" parameter
            System.out.println("Token: " + strToken);

            if (strToken.equals("test")) {
                System.out.println("valid token found");
                
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test", "test");
                token.setDetails(new WebAuthenticationDetails((HttpServletRequest) request));
                Authentication authentication = new UsernamePasswordAuthenticationToken("test", "test", authorities); //this.authenticationProvider.authenticate(token);
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                System.out.println("invalid token");
            }
        } else {
            System.out.println("no token found");
        }
        // continue thru the filter chain
        chain.doFilter(request, response);*/
    }
}
