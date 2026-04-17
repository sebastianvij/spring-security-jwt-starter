package ek.osnb.bff.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProtectedClient {
    private final RestClient restClient;

    public ProtectedClient(@Qualifier("resourceServerClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public record ProtectedDto(String message) {
    }

    public ProtectedDto getProtectedData(String jwtToken) {
        return this.restClient.get()
                .uri("/api/protected")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                .retrieve()
                .body(ProtectedDto.class);
    }
}
