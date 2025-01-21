package medilux.aquabe.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoResponse {

    @Getter
    public static class OAuthToken {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("expires_in")
        private int expiresIn;

        private String scope;

        @JsonProperty("refresh_token_expires_in")
        private int refreshTokenExpiresIn;
    }

    @Getter
    public static class KakaoProfile {
        private Long id;

        @JsonProperty("connected_at")
        private String connectedAt;

        private Properties properties;

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @Getter
        public static class Properties {
            private String nickname;

            @JsonProperty("profile_image")
            private String profileImage;

            @JsonProperty("thumbnail_image")
            private String thumbnailImage;
        }

        @Getter
        public static class KakaoAccount {
            private String email;

            @JsonProperty("is_email_verified")
            private Boolean isEmailVerified;

            @JsonProperty("has_email")
            private Boolean hasEmail;

            @JsonProperty("profile_nickname_needs_agreement")
            private Boolean profileNicknameNeedsAgreement;

            @JsonProperty("profile_image_needs_agreement")
            private Boolean profileImageNeedsAgreement;

            @JsonProperty("email_needs_agreement")
            private Boolean emailNeedsAgreement;

            @JsonProperty("is_email_valid")
            private Boolean isEmailValid;

            private Profile profile;

            @Getter
            public static class Profile {
                private String nickname;

                @JsonProperty("thumbnail_image_url")
                private String thumbnailImageUrl;

                @JsonProperty("profile_image_url")
                private String profileImageUrl;

                @JsonProperty("is_default_image")
                private Boolean isDefaultImage;

                @JsonProperty("is_default_nickname")
                private Boolean isDefaultNickname;
            }
        }
    }


}
