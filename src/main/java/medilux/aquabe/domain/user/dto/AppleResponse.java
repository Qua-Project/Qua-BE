package medilux.aquabe.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AppleResponse {
    @Data
    @AllArgsConstructor
    public static class AppleUser {
        private String email;
        private String sub;
    }
}

