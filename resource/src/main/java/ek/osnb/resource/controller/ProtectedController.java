package ek.osnb.resource.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    private static final Logger log = LoggerFactory.getLogger(ProtectedController.class);

    public record ProtectedResponse(String message) {
    }

    @GetMapping
    public ProtectedResponse getProtected(Authentication authentication) {
        String username = authentication.getName();
        return new ProtectedResponse(String.format("Hello %s - This is a protected resource!", username));
    }
}
