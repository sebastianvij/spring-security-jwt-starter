package ek.osnb.bff.controllers;

import ek.osnb.bff.clients.ProtectedClient;
import ek.osnb.bff.security.JwtTokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@GetMapping("api/protected")
class ProtectedController {
    private static final Logger log = LoggerFactory.getLogger(ProtectedController.class);
    private final ProtectedClient protectedClient;
    private final JwtTokenGenerator jwtTokenGenerator;

    ProtectedClient(ProtectedClient protectedClient, JwtTokenGenerator jwtTokenGenerator) {
        this.protectedClient = protectedClient;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @GetMapping
    ProtectedClient.ProtectedDto getProtectedData(Authentication authentication) {
        String jwtToken = jwtTokenGenerator.generate(authentication);
        log.debug("Generated token for user {}: {}", authentication.getName(), jwtToken);
        return protectedClient.getProtectedData(jwtToken);
    }
}
