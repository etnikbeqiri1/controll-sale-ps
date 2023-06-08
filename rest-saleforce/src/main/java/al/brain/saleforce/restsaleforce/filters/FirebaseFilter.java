package al.brain.saleforce.restsaleforce.filters;

import al.brain.saleforce.restsaleforce.security.FirebaseService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FirebaseFilter extends OncePerRequestFilter {
    private FirebaseService firebaseService;

    public FirebaseFilter(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        String path = request.getRequestURI();

        // skip certain paths (add any path that doesn't require authentication)
        if (path.startsWith("/public/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // Extract the token from the Bearer token
        String authToken = authHeader.substring(7);
        authToken = authToken.replaceAll("^\"|\"$", "");

        try {
            FirebaseToken token = firebaseService.verifyToken(authToken);
            // Put the FirebaseToken object into the request, it could be accessed within the server handling method if needed
            request.setAttribute("token", token);
        } catch (Exception e) {
            System.out.println(e.toString());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }

}
