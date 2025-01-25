package medilux.aquabe.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import medilux.aquabe.domain.user.dto.KakaoResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoUtil {
    @Value("${spring.kakao.auth.client}")
    private String client;
    @Value("${spring.kakao.auth.redirect}")
    private String redirect;

    public KakaoResponse.OAuthToken requestToken(String accessCode) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client);
        params.add("redirect_uri", redirect);
        params.add("code", accessCode);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        KakaoResponse.OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoResponse.OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("카카오 로그인 실패");
        }
        return oAuthToken;
    }

    public KakaoResponse.KakaoProfile requestProfile(KakaoResponse.OAuthToken oAuthToken){
        System.out.println("oAuthToken = " + oAuthToken);
        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();

        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers2.add("Authorization","Bearer "+ oAuthToken.getAccessToken());

        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = new HttpEntity <>(headers2);

        System.out.println("kakaoProfileRequest = " + kakaoProfileRequest);
        ResponseEntity<String> response2 = restTemplate2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                kakaoProfileRequest,
                String.class);

        System.out.println("response2 = " + response2);

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoResponse.KakaoProfile kakaoProfile = null;
        System.out.println("response2.getBody() = " + response2.getBody());

        try {
            kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoResponse.KakaoProfile.class);
            System.out.println("kakaoProfile = " + kakaoProfile);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("카카오 로그인 프로필 요청 실패");
        }

        return kakaoProfile;
    }
}
