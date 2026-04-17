package ek.osnb.bff.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public record UserResponse(String username, List<String> roles) {
    }

    @GetMapping
    public UserResponse getUser(Authentication authentication) {
        String subject = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .filter(role -> role.getAuthority().startsWith("ROLE_"))
                .map(role -> role.getAuthority().substring(5))
                .toList();

        if (roles.isEmpty()) {
            roles = List.of("USER");
        }

        return new UserResponse(subject, roles);
    }
}
