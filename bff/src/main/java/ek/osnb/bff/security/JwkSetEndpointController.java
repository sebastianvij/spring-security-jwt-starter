package ek.osnb.bff.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
class JwkSetEndpointController {
    private final RsaKeyProperties rsaKeyProperties;

    JwkSetEndpointController(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @GetMapping("/.well-known/jwks.json")
    public ResponseEntity<Map<String, Object>> jwks() {
        RSAPublicKey publicKey = rsaKeyProperties.publicKey();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .keyID("my-key-id")
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(KeyUse.SIGNATURE)
                .build();

        Map<String, Object> body = new JWKSet(rsaKey).toJSONObject();

        return ResponseEntity.ok(body);
    }
}
