package medilux.aquabe.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import medilux.aquabe.domain.user.dto.AppleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class AppleUtil {

    @Value("${spring.apple.client-id}")
    private String clientId;

    @Value("${spring.apple.team-id}")
    private String teamId;

    @Value("${spring.apple.key-id}")
    private String keyId;

    @Value("${spring.apple.private-key-path:#{null}}")
    private String privateKeyPath;

    @Value("${spring.apple.token-url}")
    private String tokenUrl;

    // 애플 인증 서버로부터 토큰 교환
    public AppleResponse.TokenResponse exchangeCodeForToken(String authorizationCode) {
        System.out.println("authorizationCode = " + authorizationCode);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", generateClientSecret());
        params.add("code", authorizationCode);
        params.add("grant_type", "authorization_code");

        System.out.println("params = " + params);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<AppleResponse.TokenResponse> response = restTemplate.postForEntity(tokenUrl, request, AppleResponse.TokenResponse.class);
        System.out.println("애플한테서 받은 response = " + response);
        return response.getBody();
    }

    // ID 토큰에서 사용자 정보 추출
    public AppleResponse.AppleUser getUserInfoFromToken(String idToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPrivateKey())
                .build()
                .parseClaimsJws(idToken)
                .getBody();

        System.out.println("claims = " + claims);
        String email = claims.get("email", String.class);
        String fullName = claims.get("name", String.class);

        System.out.println("email = " + email);
        System.out.println("fullName = " + fullName);

        return new AppleResponse.AppleUser(email, fullName);
    }

    public String generateClientSecret() {
        try {
            Instant now = Instant.now();
            Instant exp = now.plusSeconds(3600); // 유효기간 1시간

            // Private Key 읽기
            PrivateKey privateKey = getPrivateKey();

            // JWT 생성
            return Jwts.builder()
                    .setHeaderParam("kid", keyId) // Key ID
                    .setHeaderParam("alg", "ES256") // 알고리즘
                    .setIssuer(teamId) // Team ID
                    .setIssuedAt(Date.from(now)) // 생성 시간
                    .setExpiration(Date.from(exp)) // 만료 시간
                    .setAudience("https://appleid.apple.com") // Audience
                    .setSubject(clientId) // Client ID
                    .signWith(privateKey, SignatureAlgorithm.ES256) // 서명
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate client secret", e);
        }
    }

    // .p8 파일에서 Private Key 읽기
    private PrivateKey getPrivateKey() {
        try {
            String key;
            if(System.getenv("APPLE_PRIVATE_KEY") != null){
                key = System.getenv("APPLE_PRIVATE_KEY");
            }
            else{
                key = new String(Files.readAllBytes(Paths.get(privateKeyPath)));
            }
            key = key.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] keyBytes = Base64.getDecoder().decode(key);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key from path: " + privateKeyPath, e);
        }
    }





}
