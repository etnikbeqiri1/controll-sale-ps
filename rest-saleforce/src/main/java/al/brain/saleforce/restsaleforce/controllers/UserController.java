package al.brain.saleforce.restsaleforce.controllers;

import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<FirebaseToken> getUserProfile(HttpServletRequest request) {
        FirebaseToken firebaseToken = (FirebaseToken) request.getAttribute("token");
        String userId = firebaseToken.getEmail();
        // Retrieve user profile based on the userId or perform any desired operations
        return ResponseEntity.ok(firebaseToken);
    }
}