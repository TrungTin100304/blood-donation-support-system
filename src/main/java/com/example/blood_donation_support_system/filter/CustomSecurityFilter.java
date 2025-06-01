package com.example.blood_donation_support_system.filter;


import com.example.blood_donation_support_system.utils.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomSecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String authenHeader = request.getHeader("Authorization");
        System.out.println(authenHeader);
        if(authenHeader != null && authenHeader.startsWith("Bearer ")) {
            //Authen header có giá trị
            //Cắt chữ bearer để lấy token
            String token = authenHeader.substring(7);
            boolean isSuccess = jwtHelper.decryptToken(token);
            if(isSuccess){
                // TODO START: recheck this code snippet to see if it's correct
                String role = jwtHelper.getDataToken(token);
                System.out.println(role);
                String userName = jwtHelper.getUsername(token);
                System.out.println(userName);
                Integer userId = jwtHelper.getUserId(token);
                System.out.println(userId);
                List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                authoritiesList.add(authority);

                //Tạo ra chứng thực để bypass filter của security
                SecurityContext securityContext = SecurityContextHolder.getContext();
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken("","", authoritiesList);
                //Lưu vào context security
                securityContext.setAuthentication(authenticationToken);
                // TODO END: recheck this code snippet to see if it's correct
            }
        }
        filterChain.doFilter(request, response);
    }
}
