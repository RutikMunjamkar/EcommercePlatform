package in.org.project.EcommercePlatform.security;

import in.org.project.EcommercePlatform.entity.User;
import in.org.project.EcommercePlatform.repository.UserRepository;
import in.org.project.EcommercePlatform.util.AuthUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    AuthUtil authUtil;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=request.getHeader("Authorization");
        String methodType=request.getMethod();
        if(methodType.equalsIgnoreCase("OPTIONS") || token==null){
            filterChain.doFilter(request,response);
            return;
        }
        String userName=authUtil.getUserName(token.substring(7));
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user=userRepository.findByUserName(userName);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user,token,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request,response);
    }
}
