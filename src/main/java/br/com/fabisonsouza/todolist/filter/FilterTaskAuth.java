package br.com.fabisonsouza.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.fabisonsouza.todolist.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository user;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stu

        var servletPath = request.getServletPath();
        System.out.println("PATH" + servletPath);

        if(servletPath.startsWith("/tasks/")){
        
        // Pegar a autenticação (usuario e senha)
        var authorization = request.getHeader("Authorization");
        System.out.println("Authorization");
        System.out.println(authorization);

        

        var user_password =authorization.substring("Basic".length()).trim();

        byte[] authDecode = Base64.getDecoder().decode(authEncoded);

        var authString = new String(authDecode);

        // ["fabisonsouza", "12345"]
        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];
        // Validar o usuário
        var user = this.userRepository.findByUsername(username);
        if(user == null) {
            response.sendError(401);
        }else{
            // Validar senha
           var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
           if(passwordVerify.verified){
            filterChain.doFilter(request, response);
           }else {
            response.sendError(401);
           }
           // Segue viagem
        }    
      
    }else{
        filterChain.doFilter(request, response);
    }
}
}
