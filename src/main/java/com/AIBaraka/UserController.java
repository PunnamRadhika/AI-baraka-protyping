package com.AIBaraka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/get-by-email")
    public ResponseEntity<?> getUserByEmail(@RequestBody EmailRequest request) {
        Optional<User> userOpt = userService.findUserByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @GetMapping("/user")
    public String getUserDetails(Authentication authentication) {
        // Use the Authentication object directly
        User user = (User) authentication.getPrincipal();  // Cast to your custom User class
        String email = user.getEmail();  // Directly use email field
        return "Logged in user: " + email;
    }

    // Another option is to use SecurityContextHolder directly
    @GetMapping("/user-from-context")
    public String getUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();  // Cast to your custom User class
        return "Logged in user: " + user.getEmail();  // Use email here as well
    }
}
