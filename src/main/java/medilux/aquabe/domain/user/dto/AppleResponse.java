package medilux.aquabe.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AppleResponse {

    @Data
    public static class TokenResponse {
        private String accessToken;
        private String idToken;
        private String refreshToken;
    }

    @Data
    @AllArgsConstructor
    public static class AppleUser {
        private String email;
        private String fullName;
    }
}

